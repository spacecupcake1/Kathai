# Use an official Python runtime as a parent image
FROM python:3.8

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install any needed packages specified in requirements.txt
RUN pip install --upgrade pip
RUN pip install -r requirements.txt

# Make port 5000 available to the world outside this container
EXPOSE 5000

# Define environment variable for MongoDB URI
ENV MONGO_URI "mongodb://rigani:Modao@localhost:27017/kathai"

# Run app.py when the container launches
CMD ["python", "app.py"]
