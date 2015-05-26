function TeamViewModel() {
  function Team(name) {
    this.name = ko.observable(name);
  }

  this.teams = ko.observableArray([
    new Team("Cruzeiro"),
    new Team("Atletico MG"),
    new Team("São Paulo")
  ]);

  this.save = function(){
    $.ajax({
       url: "http://localhost:8080/api/teams/",
       type: "POST",
       crossDomain: true,
       data: ko.toJSON(name),
       dataType: "json",
       success: function (response) {
           var resp = JSON.parse(response)
           alert(resp.status);
       },
       error: function (xhr, status) {
           alert("error");
       }
     });
  };
}

ko.applyBindings(new TeamViewModel());
