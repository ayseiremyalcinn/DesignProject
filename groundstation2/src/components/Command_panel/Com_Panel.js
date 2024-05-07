import React from 'react'
import '../Command_panel/Com_Panel.css'
import SendCode from "./Send_Code/Send_Code.js";

const Com_Panel = () => {

 

  return (
    <div className='command-panel-container'>
       <div className='label-headerr'>Komut Paneli<div/>
        <SendCode/>
        </div>
    </div>
  )
}

export default Com_Panel
