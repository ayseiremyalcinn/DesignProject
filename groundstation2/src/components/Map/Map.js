import React, { useState, useEffect } from "react";
import '../Map/Map.css'
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import customIconImage from './satelliteIcon.png';
import L from 'leaflet';
import axios from 'axios';


const customIcon = new L.Icon({
  iconUrl: customIconImage,
  iconSize: [32, 32], // İkonun boyutu (örnek)
  iconAnchor: [16, 32], // İkonun tabanını belirten nokta (örnek)
  popupAnchor: [0, -32], // Pop-up'ın açıldığı yer (örnek)
});

const mapStyle = {
  height: '500px',
  width: '670px',
  marginLeft: '20px',
  marginTop: '13px'
};

const Map = () => {
  const [coordinates, setCoordinates] = useState([]);
  const [currentPosition, setCurrentPosition] = useState([39.870, 32.734]); 
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const postData = {
      "satelliteID": 25544,
      "observerLat": 39.870,
      "observerLng": 32.734,
      "observerAlt": 0,
      "seconds":20
    };
    const interval = setInterval(() => {
      axios
        .post('http://localhost:8080/positions', postData)
        .then((response) => {
          console.log("n2yo dan veri cekildi");
          const newCoordinates = response.data.map((item) => [
            item.satlatitude,
            item.satlongitude,
          ]);
          setCoordinates(newCoordinates);
          setIndex(0);
        })
        .catch((error) => {
          console.error(error);; 
        });
    }, 60000);
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      if (coordinates.length > 0) {
        setCurrentPosition(coordinates[index]);
        setIndex((prevIndex) => (prevIndex + 1) % coordinates.length);
      }
    }, 5000); 

    return () => clearInterval(interval); 
  }, [coordinates, index]);

  return (
    <div className='map-container'>
      <div className='harita-label'>Harita</div>
      <MapContainer center={currentPosition} zoom={3} style={mapStyle}>
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
      />
      <Marker position={currentPosition} icon={customIcon}>
        <Popup>
          currentPosition
        </Popup>
      </Marker>
      </MapContainer>

    </div>
  )
}

export default Map
