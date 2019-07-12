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
    <h1>Preferences</h1>
    <h3>Use the sliders to rank the following criteria, depending how important they are to you.</h3>
  </div>

  <div class="container main">
    <b class="text-center">League Table Rankings</b>
    <input type="range" class="custom-range mb-1 leagueTablePref" value="5" min="0" max="10" step="1">
    <br><br>
    <b class="text-center">Student Satisfaction</b>
    <input type="range" class="custom-range mb-1 studentSatisfactionPref" value="5" min="0" max="10" step="1">
    <br><br>
    <b class="text-center">Employability</b>
    <input type="range" class="custom-range mb-1 employabilityPref" value="5" min="0" max="10" step="1">
    <br><br>
    <b class="text-center">Research Quality</b>
    <input type="range" class="custom-range mb-1 researchQualityPref" value="5" min="0" max="10" step="1">
    <br><br>
    <b class="text-center">Student-to-Staff Ratio</b>
    <input type="range" class="custom-range mb-1 studentToStaffPref" value="5" min="0" max="10" step="1">
    <br><br>
    <b class="text-center">Cost of Living</b>
    <input type="range" class="custom-range mb-1 costOfLivingPref" value="5" min="0" max="10" step="1">
    <br><br>
    <b class="text-center">International Student Ratio</b>
    <input type="range" class="custom-range mb-1 internationalStudentPref" value="5" min="0" max="10" step="1">
    <br><br>
    <!-- <label for="customRange3">Campus</label><label for="customRange3">City</label>
    <input type="range" class="custom-range mb-1" value="5.5" min="1" max="10" step="1" id="customRange3">
    <label for="customRange3"></label>Does not Matter<label for="customRange3">  Only Russel Group</label>
    <input type="range" class="custom-range mb-1" value="5.5" min="1" max="10" step="1" id="customRange3"> -->

    <div class="text-center mb-2 mt-2 show-prefs">
      <button type="button text-center" class="btn btn-danger" onclick="send();">Get Your Personalised List!</button>
    </div>


  </div>






  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script src="scripts/main.js"></script>
</body>
</html>
