function TeamViewModel() {
  this.name = ko.observable();
}

this.teams = ko.observableArray([
  {name: 'Cruzeiro'},
  {name: 'Atletico MG'},
  {name: 'São Paulo'}
]);

this.save = function(model, event){
  var teamData = ko.toJSON(model);
  $.ajax({
     url: "http://localhost:8080/api/teams/",
     type: "POST",
     data: teamData,
     success: function (response) {
       console.log(response);
     },
     error: function (xhr, status) {
       console.log(xhr);
       console.log(status);
     }
   });
};

ko.applyBindings(new TeamViewModel());
