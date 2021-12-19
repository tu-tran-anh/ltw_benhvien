import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'antd/dist/antd.css';
import './index.css';
import { MainApp } from "./context"

ReactDOM.render(
  <MainApp>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </MainApp>,
  document.getElementById('root')
);
