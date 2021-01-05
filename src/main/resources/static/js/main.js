$(function () {

  const appendtask = function (data) {
    var taskCode = '<li><a class="task-link" data-id="' +
        data.id + '">' + data.text + '</a> '
        + '<button> <img class="task-delete" data-id="' + data.id
        + '"'
        + ' src="images/trash.png" width="16" alt="deleteImage"> </button> </li>';
    $('.task-list')
    .append('<div>' + taskCode + '</div>');
  };

  //Loading tasks on load page
  // $.get('/todolist/', function (response) {
  //   for (i in response) {
  //     appendtask(response[i]);
  //   }
  // });

  //Show adding task form
  // $('#show-add-task-form').click(function(){
  //     $('#task-form').css('display', 'flex');
  // });

  //Closing adding task form
  // $('#task-form').click(function(event){
  //     if(event.target === this) {
  //         $(this).css('display', 'none');
  //     }
  // });

  //Getting task
  // $(document).on('click', '.task-link', function(){
  //     var link = $(this);
  //     var taskId = link.data('id');
  //     $.ajax({
  //         method: "GET",
  //         url: '/todolist/' + taskId,
  //         success: function(response)
  //         {
  //             var code = '<span>Text: ' + response.text + '</span>';
  //             link.parent().append(code);
  //         },
  //         error: function(response)
  //         {
  //             if(response.status === 404) {
  //                 alert('Task not found!');
  //             }
  //         }
  //     });
  //     return false;
  // });

  //Deleted task
  $(document).on('click', '.task-delete', function () {
    var link = $(this);
    var taskId = link.data('id');
    $.ajax({
      method: "DELETE",
      url: '/todolist/' + taskId,
      success: function (response) {
        location.reload()
      },
      error: function (response) {
        if (response.status === 404) {
          alert('Task not found!');
          location.reload()
        }
      }
    });
    return false;
  });

  //Adding task
  $('#save-task').click(function () {
    var data = $('.form-signin').serialize();
    $.ajax({
      method: "POST",
      url: '/todolist/',
      data: data,
      success: function (response) {
        // $('#task-form').css('display', 'none');
        var task = {};
        task.id = response;
        var dataArray = $('.form-signin').serializeArray();
        for (i in dataArray) {
          task[dataArray[i]['name']] = dataArray[i]['value'];
        }
        appendtask(task);
        document.getElementById("text-input").value = "";
      }
    });
    return false;
  });
});