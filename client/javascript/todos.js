/**
* Function to get all the todos
*/
function getAllTodos() {
  console.log("Getting all the todos.");

  get("/api/todos", function (returned_json) {
    document.getElementById('jsonDump').innerHTML = returned_json;
  });
}

function getAllTodosByStatus() {
  console.log("Getting all the todos.");

get("/api/todos?status=" + document.getElementById("status").value, function (returned_json) {
  document.getElementById('jsonDump').innerHTML = returned_json;
});
}

function getAllTodosByBody() {
  console.log("Getting all the todos.");

get("/api/todos?contains=" + document.getElementById("contains").value, function (returned_json){
  document.getElementById('jsonDump').innerHTML = returned_json;
})
}

function getAllTodosByLimit() {
  console.log("Getting all the todos up to specified limit");

get("/api/todos?limit=" + document.getElementById("limit").value, function (returned_json){
  document.getElementById('jsonDump').innerHTML = returned_json;
})
}

function getAllTodosByOwner() {
  console.log("Getting all the todos with the specified owner");

get("/api/todos?owner=" + document.getElementById("owner").value, function (returned_json){
  document.getElementById('jsonDump').innerHTML = returned_json;
})
}

function getAllTodosByCategory() {
  console.log("Getting all the todos with the specified category");

get("/api/todos?category=" + document.getElementById("category").value, function (returned_json) {
  document.getElementById('jsonDump').innerHTML = returned_json;
})
}

// gets todos from the api.
// It adds the values of the various inputs to the requested URl to filter and order the returned todos.
function getFilteredTodos() {
  console.log("Getting all the todos.");

var url = "/api/todos?";
if (document.getElementById("owner").value != "") {
  url = url + "&owner=" + document.getElementById("owner").value;
}
if (document.getElementById("category").value != "") {
  url = url + "&category=" + document.getElementById("category").value;
}
if (document.getElementById("status").value != "") {
  url = url + "&status=" + document.getElementById("status").value;
}
if (document.getElementById("contains").value != "") {
  url = url + "&contains=" + document.getElementById("contains").value;
}
if (document.getElementById("orderBy").value != "") {
  url = url + "&orderBy=" + document.getElementById("orderBy").value;
}
if (document.getElementById("limit").value != "") {
  url = url + "&limit=" + document.getElementById("limit").value;
}

get(url, function (returned_json) {
  document.getElementById('jsonDump').innerHTML = syntaxHighlight(JSON.stringify(JSON.parse(returned_json), null, 2));
});
}
