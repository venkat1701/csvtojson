import React, { useState } from 'react';
import axios from 'axios';

const FileUpload = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [uploadStatus, setUploadStatus] = useState('');

  // Handle file selection
  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  // Handle file upload
  const handleUpload = async () => {
    if (!selectedFile) {
      alert('Please select a file first!');
      return;
    }

    const formData = new FormData();
    formData.append('file', selectedFile);

    try {
      const response = await axios.post('http://localhost:8080/api/csv/convert', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      setUploadStatus('File uploaded successfully! Download the JSON file.');
    } catch (error) {
      console.error('Error uploading file:', error);
      setUploadStatus('Error uploading file');
    }
  };

  return (
    <div style={{ padding: '20px', textAlign: 'center' }}>
      <h2>CSV to JSON Converter</h2>
      <input type="file" onChange={handleFileChange} accept=".csv" />
      <button onClick={handleUpload} style={{ marginLeft: '10px' }}>
        Upload
      </button>
      <div style={{ marginTop: '20px' }}>
        {uploadStatus && <p>{uploadStatus}</p>}
      </div>
    </div>
  );
};

export default FileUpload;
