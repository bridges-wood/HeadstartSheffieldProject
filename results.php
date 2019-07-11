<?php

  function checkIfPermalinkExists($p) {
    return false;
  }

  function generateRandomString($length = 10) {
    // https://stackoverflow.com/questions/4356289/php-random-string-generator
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
      $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
  }

  $permalink = generateRandomString();
  while (checkIfPermalinkExists($permalink)) {
    $permalink = generateRandomString();
  }

 ?>

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
  <div class="heading">
    <h1 class="text-center">Your Personalised Rankings</h1>
    <p class="text-center">Click on any individual university to reveal more information.</p>
    <br>
    <br>
    <p class="text-center" style="width: 100%;">Use this link to come back to your results at any time: <input style="width: 280px;" type="text" value="http://localhost/results.php?r=<?php echo $permalink; ?>"></p>

  </div>

  <div class="container main">
    <div class="accordion" id="ranking-table"></div>
  </div>


  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script src="scripts/main.js"></script>
  <script>
    var unis = [
      {
        'id': 'cambridge',
        'name': 'University of Cambridge',
        'info': 'UoC',
        'logo': 'https://logos-download.com/wp-content/uploads/2016/09/University_of_Cambridge_crest_logo.png'
      },
      {
        'id': 'standrews',
        'name': 'University of St Andrews',
        'info': 'UoSA',
        'logo': 'resources/standrews-logo.png'
      },
      {
        'id': 'hull',
        'name': 'University of Hull',
        'info': 'UoH',
        'logo': 'https://uk.campusclothing.com/Uploads/University/Images/63.jpg'
      }
    ]
    setRankings(unis);
  </script>
</body>
</html>
