<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link rel="stylesheet" href="styles/main.css">
  <title>MyRanker</title>
</head>
<body>
  <div class="heading text-center">
    <div>
      <img class="logo" src="resources/myranker-logo.png" alt="MyRanker Logo">
    </div>
    <h1>Course</h1>
    <h3>Select the course you are interested in studying.</h3>
  </div>
  <div class="container main">
    <div class="alevel-container container"></div>
    <select class="courses form-control">
      <option selected>Course</option>
    </select>
    <div class="text-center mt-2 mb-2 show-prefs">
      <button class="btn btn-danger text-center" onclick="showPrefs();">Next &rarr;</button>
    </div>
  </div>

  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script src="scripts/main.js"></script>
  <script>
    setCourses();
  </script>
</body>
</html>
