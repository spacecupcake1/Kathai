function openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
  }
  
  function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
  }

  var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var dropdownContent = this.nextElementSibling;
    if (dropdownContent.style.display === "block") {
      dropdownContent.style.display = "none";
    } else {
      dropdownContent.style.display = "block";
    }
  });
}

function fetchBookCover(title, author) {
  // Construct the URL for the Google Books API
  const apiUrl = `https://www.googleapis.com/books/v1/volumes?q=${title}+inauthor:${author}`;

  // Send a GET request to the API
  fetch(apiUrl)
      .then(response => response.json())
      .then(data => {
          // Extract the cover image URL from the API response
          const imageUrl = data.items[0]?.volumeInfo?.imageLinks?.thumbnail;

          // Update the src attribute of the img element
          const bookCover = document.getElementById('bookCover');
          if (imageUrl) {
              bookCover.src = imageUrl;
          } else {
              bookCover.src = 'placeholder-image-url.jpg'; // You can set a placeholder image here
          }
      })
      .catch(error => {
          console.error('Error fetching book cover:', error);
      });
}

document.addEventListener('DOMContentLoaded', function() {
  // Get the text content of title and author elements
  const title = document.getElementById('titleSpan');
  const author = document.getElementById('authorSpan');

  // Call the function with the book title and author
  fetchBookCover(title.textContent, author.textContent);
});

