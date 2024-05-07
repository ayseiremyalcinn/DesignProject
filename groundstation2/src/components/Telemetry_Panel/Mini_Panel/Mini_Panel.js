import React, { Component } from 'react'
import '../Mini_Panel/Mini_Panel.css'

class Mini_Panel extends Component {
    render(){
        const {header, value} = this.props;
        return( 
            <div className='mini-pan'>
                <div className='head-label'>{header}</div>
                <div className='value-label'>{value}</div>
            </div>
        )   
    }
}

export default Mini_Panel;