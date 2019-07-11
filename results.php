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

  if (!isset($_GET['r'])) {
    $permalink = generateRandomString();
    while (checkIfPermalinkExists($permalink)) {
      $permalink = generateRandomString();
    }
  }

  $bc = $_GET['bc'];

  $fp = stream_socket_client("tcp://localhost:7000", $errno, $errstr, 5);
  if (!$fp) {
      echo "$errstr ($errno)<br />\n";
  } else {
    fwrite($fp, $bc . "\n");
    $c = fgets($fp, 500);
    fclose($fp);
    $c = substr($c, 2);
    $d = '';
    while ($c[0] != ']') {
      $d .= $c[0];
      $c = substr($c, 1);
    }
    $d .= ']';

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
  <div class="heading text-center">
    <div>
      <img class="logo" src="resources/myranker-logo.png" alt="MyRanker Logo">
    </div>
    <h1>Your Personalised Rankings</h1>
    <p>Click on any individual university to reveal more information.</p>
    <br>
    <br>
    <p style="width: 100%;">Use this link to come back to your results at any time: <input style="width: 280px;" type="text" value="http://localhost/results.php?r=<?php echo $permalink; ?>"></p>
    <br>
    <br>
    <div>
      <button class="btn btn-info" onclick="tweet('<?php echo $permalink; ?>');">Tweet your results!</button>
    </div>
  </div>

  <div class="container main">
    <div class="accordion" id="ranking-table"></div>
  </div>
  <br>
  <br>


  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script src="scripts/main.js"></script>
  <script>

    $('unis.json', function(data) {
      var u = <?php echo $d; ?>;
      var v = [];
      var keys = Object.keys();

      for (var i = 0; i < keys.length; i++){
        var key = keys[i];
        var value = data[key];
        
      }

        v.push({
          'id': u[i],
          'name': u[i],
          'info': u[i]
        });
      }
      setRankings(v);
    });


    var l = [
      {
        "id": "cambridge",
        "name": "University of Cambridge",
        "info": "UoC"
      },
      {
        "id": "st_andrews",
        "name": "University of St Andrews",
        "info": "UoSA"
      },
      {
        "id": "hull",
        "name": "University of Hull",
        "info": "UoH"
      }
    ];



  </script>
</body>
</html>
