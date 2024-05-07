import React, { useState, useEffect } from "react";
import '../Passes/Table_pass.css'
import axios from 'axios';
import { LineChart } from '@mui/x-charts/LineChart';

function timestampToUTC(timestamp) {
  var date = new Date(timestamp * 1000);

  var hours = date.getUTCHours().toString().padStart(2, '0');
  var minutes = date.getUTCMinutes().toString().padStart(2, '0');
  var seconds = date.getUTCSeconds().toString().padStart(2, '0');

  var utcTime = hours + ':' + minutes + ':' + seconds;

  return utcTime;
}

const Table_pass = () => {

  const [N2YOPassesData, setN2YOPassesData] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [aData, setAData] = useState([0,1,2]);
  const [eData, setEData] = useState([1,2,3]);
  const [xLabels, setXLabels] = useState([0,1,2]);
  const postData = {
    "satelliteID": 25544,
    "observerLat": 39.870,
    "observerLng": 32.734,
    "observerAlt": 0,
    "days":2,
    "minVisibility": 100
  };

  useEffect(() => {
    axios
      .post('http://localhost:8080/visualpasses', postData)
      .then((response) => {
        console.log(response);
        if (response.data.body.passes.length > 0) {
          const firstPass = response.data.body.passes[0];
          setN2YOPassesData(response.data.body.passes);
          const azimuth = [firstPass.startAz, firstPass.maxAz, firstPass.endAz];
          const elevation = [firstPass.startEl, firstPass.maxEl, firstPass.endEl];
          const time = [timestampToUTC(firstPass.startUTC),
                        timestampToUTC(firstPass.maxUTC),
                        timestampToUTC(firstPass.endUTC)];
          setAData(azimuth);
          setEData(elevation);
          setXLabels(time);
        } else {
          setError("Beklenen veri alınamadı, lütfen tekrar deneyin.");
        }
        setLoading(false); 
      })
      .catch((error) => {
        console.error(error);
        setError("Veri alınamadı, lütfen tekrar deneyin.");
        setLoading(false); 
      });
  }, []);

  if (loading) {
    return <div>Yükleniyor...</div>; 
  }

  if (error) {
    return <div>{error}</div>; 
  }

  if (!Array.isArray(N2YOPassesData) || N2YOPassesData.length === 0) {
    return <div>Gösterilecek geçiş yok.</div>; 
  } 
  
  const tableStyle = {
    borderCollapse: 'collapse',
    width: '100%',
    backgroundColor: 'transparent',
  };
  const tableContainerStyle = {
    maxWidth: '370px', 
    maxHeight: '250px', 
    overflow: 'auto', 
  };

  const cellStyle = {
    padding: '8px',
    color: 'white', 
    fontSize: '12px',
  };

  return (
    <div className="container-pass">
      <div className='table-container'>
      <div className='label-header'>Yakındaki Geçişler</div>
      <div style={tableContainerStyle}>
        <table style={tableStyle}>
          <thead>
            <tr>
              <th style={cellStyle}>Başlangıç Azimuth</th>
              <th style={cellStyle}>Başlangıç Compass</th>
              <th style={cellStyle}>Başlangıç Elevation</th>
              <th style={cellStyle}>Başlangıç UTC</th>
              <th style={cellStyle}>Maksimum Azimuth</th>
              <th style={cellStyle}>Maksimum Compass</th>
              <th style={cellStyle}>Maksimum Elevation</th>
              <th style={cellStyle}>Maksimum UTC</th>
              <th style={cellStyle}>Bitiş Azimuth</th>
              <th style={cellStyle}>Bitiş Compass</th>
              <th style={cellStyle}>Bitiş Elevation</th>
              <th style={cellStyle}>Bitiş UTC</th>
              <th style={cellStyle}>Süre</th>
              <th style={cellStyle}>Başlangıç Görünürlüğü</th>
            </tr>
          </thead>
          <tbody>
            {N2YOPassesData.map((pass, index) => (
              <tr key={index}>
                <td style={cellStyle}>{pass.startAz}</td>
                <td style={cellStyle}>{pass.startAzCompass}</td>
                <td style={cellStyle}>{pass.startEl}</td>
                <td style={cellStyle}>{pass.startUTC}</td>
                <td style={cellStyle}>{pass.maxAz}</td>
                <td style={cellStyle}>{pass.maxAzCompass}</td>
                <td style={cellStyle}>{pass.maxEl}</td>
                <td style={cellStyle}>{pass.maxUTC}</td>
                <td style={cellStyle}>{pass.endAz}</td>
                <td style={cellStyle}>{pass.endAzCompass}</td>
                <td style={cellStyle}>{pass.endEl}</td>
                <td style={cellStyle}>{pass.endUTC}</td>
                <td style={cellStyle}>{pass.duration}</td>
                <td style={cellStyle}>{pass.startVisibility}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      </div>
      <div className="chart-container">
      <div className='label-header'>Yer istasyonu Anteni</div>
      <LineChart
        width={390}
        height={180}
        series={[
          { data: aData, label: 'azimut', area: true, stack: 'total', showMark: true, color: '#6C8B8B'},
          { data: eData, label: 'elevasyon', area: true, stack: 'total', showMark: true, color:'#C5A6C4'},
        
        ]}
        xAxis={[{ scaleType: 'point', data: xLabels, color: '#C5A6C4' }]}
        sx={{
          '.recharts-text': {
            fontFamily: 'Arial, sans-serif', // Özel yazı tipi
            fill: 'white',       // Özel yazı rengi
          },
          '.recharts-line': {
            stroke: 'white',     // Grafik rengi
          },
        }}
      />
    </div>
    </div>
  );
};

export default Table_pass;