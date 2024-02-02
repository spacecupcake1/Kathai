from flask import Flask, render_template
from pymongo import MongoClient
from bson import ObjectId

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

@app.route('/book/<int:id>')
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


if __name__ == '__main__':
    app.run(debug=True)
