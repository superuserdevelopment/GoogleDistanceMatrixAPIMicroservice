# GoogleDistanceMatrixAPIMicroservice
A Spring Boot project for Google Maps Directions API to find road distance between two points based on location name or pin code.<br>Made with ♥ in Java
## Steps to excution:
<ol>
<li>Execute the DemoApplication.java file
<li>An Tomcat server instance will be started at <b>port: 8080</b>.
<li>Access this server through your browser using localhost:8080.
<li>To use the API enter the following in the url:
<ol type='a'>
<li>JSON String: <a href="">http://localhost:8080/distance/json/{start}to{end}</a><br><i>Replace {start} and {end} with respective places/pin-codes.</i></li>
<li>Plain String: <a href="">http://localhost:8080/distance/{start}to{end}</a><br><i>Replace {start} and {end} with respective places/pin-codes.</i></li>
</ol>
<li>Get output.
</ol>

## Output Format
<ol>
 <li><b>JSON String</b><br>Sample input: <a href="">http://localhost:8080/distance/json/MumbaitoThane</a><br>
   
   ```json
   {
      "Starting Location":"Mumbai, Maharashtra, India",
      "Duration":"32 mins",
      "Ending Location":"Thane, Maharashtra, India",
      "Distance":"22.7 km"
   }
   ```
  </li>
  <li><b>Plain String</b><br>Sample input: <a href="">http://localhost:8080/distance/MumbaitoThane</a><br>
  
  ```
  Distance is: 22.7 km
  Duration is: 32 mins
  Starting Location: Mumbai, Maharashtra, India
  Ending Location: Thane, Maharashtra, India
  ```
  </li>
</ol>
