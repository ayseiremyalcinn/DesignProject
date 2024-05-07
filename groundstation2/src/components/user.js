import React, { Component } from 'react'

class user extends Component {

  static defaultProps = {
    name : "isim yok",
    school : "okul yok",
    clas : "sinif yok"
  }

  state = {
    isVisible : true
  }


  

  render() {

    const {name, school, clas} = this.props;
    return (
      <div>  
        <h4>"hey" onClick =  </h4>
      {
        this.state.isVisible ? 
        
        <ul>
          <li>isim: {name} <i className="fa-solid fa-fire-extinguisher"></i></li>
          <li>okul: {school}</li>
          <li>sinif: {clas} </li>
        </ul> : null
      }
      </div>
    )
  }
}

export default user
