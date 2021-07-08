
function getIndex(list, id) {
  for (var i = 0; i < list.length; i++ ) {
    if (list[i].id === id) {
      return i;
    }
  }

  return -1;
}


var messageApi = Vue.resource('/message{/id}');

Vue.component('message-form', {
  props: ['messages', 'messageAttr'],
  data: function() {
    return {
      text: '',
      id: ''
    }
  },
  watch: {
    messageAttr: function(newVal, oldVal) {
      this.text = newVal.text;
      this.id = newVal.id;
    }
  },
  template:
      '<div>' +
      '<input type="text" placeholder="Write something" v-model="text" />' +
      '<input type="button" value="Save" @click="save" />' +
      '</div>',
  methods: {
    save: function() {
      var message = { text: this.text };

      if (this.id) {
        messageApi.update({id: this.id}, message).then(result =>
            result.json().then(data => {
              var index = getIndex(this.messages, data.id);
              this.messages.splice(index, 1, data);
              this.text = ''
              this.id = ''
            })
        )
      } else {
        messageApi.save({}, message).then(result =>
            result.json().then(data => {
              this.messages.push(data);
              this.text = ''
            })
        )
      }
    }
  }
});

Vue.component('message-row', {
  props: ['message', 'editMethod', 'messages'],
  template: '<div>' +
      '{{ message.text }}' +
      '<span style="position: absolute; right: 0">' +
      '<input type="button" value="Edit" @click="edit" />' +
      '<input type="button" value="X" @click="del" />' +
      '</span>' +
      '</div>',
  methods: {
    edit: function() {
      this.editMethod(this.message);
    },
    del: function() {
      messageApi.remove({id: this.message.id}).then(result => {
        if (result.ok) {
          this.messages.splice(this.messages.indexOf(this.message), 1)
        }
      })
    }
  }
});

Vue.component('messages-list', {
  props: ['messages'],
  data: function() {
    return {
      message: null
    }
  },
  template:
      '<div style="position: relative; width: 300px;">' +
      '<message-form :messages="messages" :messageAttr="message" />' +
      '<message-row v-for="message in messages" :key="message.id" :message="message" ' +
      ':editMethod="editMethod" :messages="messages" />' +
      '</div>',
  created: function() {
    messageApi.get().then(result =>
        result.json().then(data =>
            data.forEach(message => this.messages.push(message))
        )
    )
  },
  methods: {
    editMethod: function(message) {
      this.message = message;
    }
  }
});

var app = new Vue({
  el: '#app',
  template: '<messages-list :messages="messages" />',
  data: {
    messages: []
  }
});





//
// $(function () {
//
//   const appendtask = function (data) {
//     var taskCode = '<li><a class="task-link" data-id="' +
//         data.id + '">' + data.text + '</a> '
//         + '<button type="button" id="delete-task" class="btn btn-xs btn-danger" data-id="'
//         + data.id + '"> '
//         + ''
//         + ' <img src="images/trash.png" width="16" alt="deleteImage"> </button> </li>';
//     $('.task-list')
//     .append('<div>' + taskCode + '</div>');
//   };
//
//   //Deleted task
//   $(document).on('click', '#delete-task', function () {
//     var link = $(this);
//     var taskId = link.data('id');
//     $.ajax({
//       method: "DELETE",
//       url: '/todolist/' + taskId,
//       success: function (response) {
//         location.reload()
//       },
//       error: function (response) {
//         if (response.status === 404) {
//           alert('Task not found!');
//           location.reload()
//         }
//       }
//     });
//     return false;
//   });
//
//   //Adding task
//   $('#save-task').click(function () {
//     var data = $('.form-signin').serialize();
//     $.ajax({
//       method: "POST",
//       url: '/todolist/',
//       data: data,
//       success: function (response) {
//         // $('#task-form').css('display', 'none');
//         var task = {};
//         task.id = response;
//         var dataArray = $('.form-signin').serializeArray();
//         for (i in dataArray) {
//           task[dataArray[i]['name']] = dataArray[i]['value'];
//         }
//         appendtask(task);
//         document.getElementById("text-input").value = "";
//       }
//     });
//     return false;
//   });
// });

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