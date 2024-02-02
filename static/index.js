function openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    sidebar.style.marginLeft = "250px";
  }
  
  function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    sidebar.style.marginLeft = "0";
  }
  
  var dropdown = document.getElementsByClassName("dropdown-btn");
  var i;
  
  for (i = 0; i < dropdown.length; i++) {
    dropdown[i].addEventListener("click", function () {
      console.log("Button Clicked");
      this.classList.toggle("active");
      var dropdownContent = this.nextElementSibling;
      if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none";
      } else {
        dropdownContent.style.display = "block";
      }
    });
  
    console.log(dropdown);
  
  }
  
  document.addEventListener('DOMContentLoaded', function () {
  
    function openNav() {
      document.getElementById("mySidebar").style.width = "250px";
      document.getElementById("main").style.marginLeft = "250px";
    }
  
    function closeNav() {
      document.getElementById("mySidebar").style.width = "0";
      document.getElementById("main").style.marginLeft = "0";
    }
  
    var dropdown = document.getElementsByClassName("dropdown-btn");
    var i;
  
    for (i = 0; i < dropdown.length; i++) {
      dropdown[i].addEventListener("click", function () {
        console.log("Button Clicked");
        this.classList.toggle("active");
        var dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === "block") {
          dropdownContent.style.display = "none";
        } else {
          dropdownContent.style.display = "block";
        }
      });
  
      console.log(dropdown);
  
    }
  
    function fetchBookCoverByISBN(isbn) {
      const apiUrl = `https://www.googleapis.com/books/v1/volumes?q=isbn:${isbn}`;
  
      fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
          // Check if data.items is defined and not empty
          if (data.items && data.items.length > 0) {
            const imageUrl = data.items[0].volumeInfo?.imageLinks?.thumbnail;
            const bookCover = document.getElementById('bookCover');
            if (imageUrl) {
              bookCover.src = imageUrl;
            } else {
              bookCover.src = 'https://t3.ftcdn.net/jpg/04/62/93/66/360_F_462936689_BpEEcxfgMuYPfTaIAOC1tCDurmsno7Sp.jpg';
            }
          } else {
            console.error('No book data found');
          }
        })
        .catch(error => {
          console.error('Error fetching book cover:', error);
        });
    }
    // Assuming that isbnSpan is an existing element
    var isbn = document.getElementById('isbnSpan');
    fetchBookCoverByISBN(isbn.textContent);
  
  });