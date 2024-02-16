from flask import Flask, render_template, request, redirect, url_for, flash, session, request
from pymongo import MongoClient
from bson.json_util import dumps
from flask_pymongo import PyMongo
from flask_paginate import Pagination, get_page_parameter
import pymongo

app = Flask(__name__)

# Set a secret key for session management
app.config['MONGO_URI'] = 'mongodb://rigani:Modao@localhost:27017/kathai'
app.secret_key = 'Sizhui'

mongo = PyMongo(app)

# Connect to MongoDB
client = MongoClient('mongodb://rigani:Modao@localhost:27017/kathai')
db = client['kathai']
collection = db.feb



@app.route('/all')
def index():

    # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))

    # Find all documents in the collection
    documents = collection.find()
    # Render the HTML template with the documents
    return render_template('index.html', documents=documents)

@app.route('/books/')
def booklist():

  # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))

    # Find all documents in the collection
    all_documents = collection.find().sort('Title', pymongo.ASCENDING)

    # Pagination settings
    page = request.args.get(get_page_parameter(), type=int, default=1)
    per_page = 50  # You can adjust this to change the number of items per page
    offset = (page - 1) * per_page

    # Get a subset of documents for the current page
    documents = all_documents[offset:offset + per_page]

    # Get the total count using count_documents
    total_documents = collection.count_documents({})

    # Create Pagination object
    pagination = Pagination(page=page, total=total_documents, per_page=per_page,
                            css_framework='bootstrap4')  # Adjust the CSS framework as needed

    # Render the HTML template with the paginated documents
    return render_template('booklist.html', documents=documents, pagination=pagination)

@app.route('/', methods=['GET', 'POST'])
def bookfilter():
    # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))

    # Initialize query parameters
    title_query = request.args.get('title', '')
    author_query = request.args.get('author', '')
    genre_query = request.args.get('genre', '')
    rating_query = request.args.get('rating', '')

    # Construct query based on user input
    query = {}
    if title_query:
        query['Title'] = {'$regex': f'.*{title_query}.*', '$options': 'i'}
    if author_query:
        query['Authors'] = {'$regex': f'.*{author_query}.*', '$options': 'i'}
    if genre_query:
        query['Bookshelf'] = genre_query
    if rating_query:
        query['Rating'] = float(rating_query)

    # Find documents matching the query in the collection
    documents = collection.find(query)

    # Get distinct genres and ratings for dropdowns
    distinct_genres = collection.distinct('Bookshelf')
    distinct_ratings = collection.distinct('Rating')

    # Render the HTML template with the documents and query parameters
    return render_template('bookfilter.html', documents=documents, title_query=title_query,
                           author_query=author_query, genre_query=genre_query, rating_query=rating_query,
                           distinct_genres=distinct_genres, distinct_ratings=distinct_ratings)

@app.route('/books/<int:id>')
def book_desc(id):

    # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))
    
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
        return render_template('error.html')
    
    
    
@app.route('/books/read')
def ReadAnalysis():

    # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))
    
     # Count by Read
    total = collection.count_documents({})
    print(f"Total Books: {total}")

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
    readCounts = {"read": read_count, "unread": not_read_count}
    pageCounts = {"read": aggregation_result['pages_read'], "unread": pages_unread}

    return render_template('ReadAnalysis.html', readCounts=readCounts, pageCounts=pageCounts, total=total)

@app.route('/books/rating')
def RatingAnalysis():

    # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))
    
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

    # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))
    
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

@app.route('/books/format')
def FormatAnalysis():
    
    # Check if the user is logged in
    if 'user_id' not in session:
        flash('Please log in to access this page', 'error')
        return redirect(url_for('show_login_form'))
    
    # Aggregation pipeline to group by ratings and count
    pipeline = [
        {
            '$match': {
                'Format': {'$ne': None}  # Exclude documents where Rating is null
            }
        },
        {
            '$group': {
                '_id': '$Format',
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

    return render_template('FormatAnalysis.html', result_json=result_json)


@app.route('/login')
def show_login_form():
    user = {}  # Assuming you want to pass an empty user dictionary to the template
    return render_template('login.html', user=user)

@app.route('/login', methods=['POST'])
def login():
    if request.method == 'POST':
        user_name = request.form.get('userName')
        password = request.form.get('password')
        
        # Implement authentication logic with MongoDB here
        # For simplicity, let's assume you have a 'users' collection in MongoDB
        
        user = mongo.db.users.find_one({'userName': user_name, 'password': password})
        
        if user:
            session['user_id'] = str(user['_id'])
            flash('Login successful!', 'success')
            return redirect(url_for('index'))
        else:
            flash('Invalid username or password', 'error')
            return render_template('error.html')

@app.route('/register', methods=['POST'])
def register():
    if request.method == 'POST':
        # Get user input from the registration form
        first_name = request.form.get('firstName')
        last_name = request.form.get('lastName')
        user_name = request.form.get('userName')
        email = request.form.get('email')
        password = request.form.get('password')

        # Check if the username is already taken (you might want to do this differently in production)
        if mongo.db.users.find_one({'userName': user_name}):
            flash('Username already exists', 'error')
            return redirect(url_for('index'))

        # Insert the user into the 'users' collection in MongoDB
        mongo.db.users.insert_one({
            'firstName': first_name,
            'lastName': last_name,
            'userName': user_name,
            'email': email,
            'password': password
        })

        flash('Registration successful! Please log in.', 'success')
        return redirect(url_for('login'))

@app.route('/logout')
def logout():
    # Clear the session variable to log out the user
    session.pop('user_id', None)
    flash('Logged out successfully', 'success')
    return redirect(url_for('show_login_form'))



if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
