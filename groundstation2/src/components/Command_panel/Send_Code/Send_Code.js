import React, { useState, useRef } from 'react';
import '../Send_Code/Send_Code.css'
import axios from 'axios';

const Send_Code = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [sendTime, setSendTime] = useState('');
  const [fileContent, setFileContent] = useState('');

  const fileInputRef = useRef(null);

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setSelectedFile(file);

    if (file) {
      // Dosyanın içeriğini oku
      const reader = new FileReader();
      reader.onload = (e) => {
        setFileContent(e.target.result);
      };
      reader.readAsText(file);
    } else {
      setFileContent('');
    }
  };

  const handleSendTimeChange = (event) => {
    setSendTime(event.target.value);
  };

  const handleFileSubmit = () => {
    let delay = 100;
    if(sendTime === "1")  delay = 60000;
    else if(sendTime === "2") delay = 120000;

    setTimeout(() => {
      axios
      .post('http://localhost:8080/uploadCode', selectedFile)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.error(error);
      });
    }, delay); 

    console.log('Dosya gönderildi:', selectedFile);
    console.log('Gönderme zamanı:', sendTime);
  };

  return (
    <div className="Send_Code">
      <div className="button-container">
        <input type="file" ref={fileInputRef} style={{ display: 'none' }} onChange={handleFileChange} />
        <button onClick={() => fileInputRef.current.click()}>Kod Dosyası Seç</button>
        <button onClick={handleFileSubmit}>Dosyayı Gönder</button>
      </div>
      <div className="combobox-container">
        Gönderme zamanı:
        <select value={sendTime} onChange={handleSendTimeChange}>
          <option value="1">1 dakika sonra</option>
          <option value="2">2 dakika sonra</option>
        </select>
      </div>
      <div className="message-box">
        {selectedFile && (
          <>
          <span> {selectedFile.name}</span>
          <div>{fileContent}</div>
          </>
        )}
      </div>     

    </div>
  );
};

export default Send_Code;