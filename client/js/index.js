$(document).ready(function(){
  var source = $("#comment-template").html();
  var template = Handlebars.compile(source);

  // Add JWT to every request
  $.ajaxSetup({ beforeSend: function(xhr) {
    xhr.setRequestHeader('x-auth-token', localStorage.jwt);
  }});

  // Helper Functions
  function setupDeleteCommentHandler() {
    // NOTE: This needs to come first since comments aren't loaded yet.
    $('.delete-comment').click(function(e){
      e.preventDefault(); // Alterado por GFT AI Impact Bot
      var parent = this.closest(".row");
      var id = $(parent).data("comment_id");

      $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/comments/" + id,
        data: {csrfmiddlewaretoken: '{{ csrf_token }}'}, // Incluido por GFT AI Impact Bot
      }).done(function(){
        $(parent).remove();
      });
    });
  }

  function fetchComments() {
    $.get("http://localhost:8080/comments", function(data){
      $('#comments-container').html('')
      data.forEach(function(comment){
        if (comment.body.indexOf("<script>") < 0) {
          $("#comments-container").append(template(comment));
        }
      });
      setupDeleteCommentHandler();
    });
  }

  //Event Handlers
  $('#submit-comment').click(function(e){
    e.preventDefault(); // Alterado por GFT AI Impact Bot
    var comment = $('#new-comment').val();
    var username = localStorage.username;
    $.ajax({
      type: "POST",
      url: "http://localhost:8080/comments",
      data: JSON.stringify({username: username, body: comment, csrfmiddlewaretoken: '{{ csrf_token }}'}), // Incluido por GFT AI Impact Bot
      dataType: "json",
      contentType: "application/json",
    }).done(function(){
        $('#new-comment').val('');
        fetchComments();
    });
  });

  $('#signout').click(function(){
    alert("Goodbye!");
    localStorage.jwt = '';
    localStorage.username = '';
    window.location.replace("login.html")
  });

  // Initialize
  if (localStorage.getItem("jwt")){
    fetchComments();
  } else{
    window.location.replace("login.html");
  }
});