
<?php
$fp = stream_socket_client("tcp://localhost:7000", $errno, $errstr, 5);
if (!$fp) {
    echo "$errstr ($errno)<br />\n";
} else {
  fwrite($fp, "{leagueTablePref:3,studentSatisfactionPref:6,employabilityPref:4,researchQualityPref:7,studentToStaffPref:4,costOfLivingPref:6,internationalStudentPref:3}\n");
  $c = fgets($fp, 500);
  fclose($fp);
  $c = substr($c, 2);
  $d = '';
  while ($c[0] != ']') {
    $d .= $c[0];
    $c = substr($c, 1);
  }
  $d .= ']';
  echo $d;
}
?>
