from flask import Flask, render_template
from pymongo import MongoClient
from bson.json_util import dumps
import  json

app = Flask(__name__)

# Connect to MongoDB
client = MongoClient('mongodb://rigani:Modao@localhost:27017/kathai')
db = client['kathai']
collection = db.janu

@app.route('/')
def index():

    # Find all documents in the collection
    documents = collection.find()

    # Render the HTML template with the documents
    return render_template('index.html', documents=documents)



@app.route('/books/<int:id>')
def book_desc(id):
    print(f"Received book_id: {id}")

    # Print debug information
    print(f"Looking for book with Id: {id}")

    # Find the book by Id
    document = collection.find_one({'Id': id})

    # Check if the book exists
    if document:
        print(f"Book found: {document}")
        return render_template('book_desc.html', document=document)
    else:
        print("Book not found")
        return render_template('book_not_found.html')
    
    
    
@app.route('/books/read')
def ReadAnalysis():

     # Count by Read
    read_count = collection.count_documents({'Read': 1})
    not_read_count = collection.count_documents({'Read': 0})

    # Pages Read and Page Count
    result = collection.aggregate([
        {
            '$group': {
                '_id': None,
                'pages_read': {'$sum': '$Pages_Read'},
                'total_pages': {'$sum': '$Pages'}
            }
        }
    ])

    # Extract the results from the aggregation
    aggregation_result = next(result, None)

    # Calculate 'pages_unread' as 'total_pages' minus 'pages_read'
    if aggregation_result:
     pages_unread = aggregation_result['total_pages'] - aggregation_result['pages_read']
    
    # Assuming readCounts and pageCounts are properly defined earlier in your code
    readCounts = {"Read": read_count, "Not Read": not_read_count}
    pageCounts = {"read": aggregation_result['pages_read'], "unread": pages_unread}

    return render_template('ReadAnalysis.html', readCounts=readCounts, pageCounts=pageCounts)



@app.route('/books/rating')
def RatingAnalysis():

    # Aggregation pipeline to group by ratings and count
    pipeline = [
        {
            '$match': {
                'Rating': {'$ne': None}  # Exclude documents where Rating is null
            }
        },
        {
            '$group': {
                '_id': '$Rating',
                'count': {'$sum': 1}
            }
        }
    ]

    # Execute the aggregation pipeline
    result = collection.aggregate(pipeline)

    # Convert the result to a list and print it
    result_list = list(result)

    result_json = dumps(result_list, default=str) 

    return render_template('RatingAnalysis.html', result_json=result_json)

@app.route('/books/genre')
def GenreAnalysis():

    # Aggregation pipeline to group by ratings and count
    pipeline = [
        {
            '$group': {
                '_id': '$Bookshelf',
                'count': {'$sum': 1}
            }
        }
    ]

    # Execute the aggregation pipeline
    result = collection.aggregate(pipeline)

    # Convert the result to a list and print it
    result_list = list(result)

    result_json = dumps(result_list, default=str) 

    print(result_json)

    return render_template('GenreAnalysis.html', result_json=result_json)


if __name__ == '__main__':
    app.run(debug=True)
