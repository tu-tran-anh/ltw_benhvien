import React, { useState,useEffect } from "react";
import { Modal, Button, Form } from "antd";
import AddDoctorForm from "./AddDoctorForm";

export default function AddDotor({ isModalVisible, setIsModalVisible, data, handleRefresh}) {
  // const [isModalVisible, setIsModalVisible] = useState(false);
  const [message, setMessage] = useState("");

  const handleChange = (setState, value) => {
    setState(value);
  };
  useEffect(() => {
    console.log("vao effect");
    setTimeout(()=>{
      setMessage("");
    },5000)
  }, [message])
  const sendMessage = (str)=>{
    setMessage(str);
  }

  return (
    <div>
      <Modal
        visible={isModalVisible}
        // onOk={()=>{handleChange(setIsModalVisible,false)}}
        onCancel={() => {
          setIsModalVisible(false);
        }}
        width = {800}
        footer={[
          message==""?null:<p>{message}</p>
        ]}
      >
        <AddDoctorForm sendMessage={sendMessage} data={data} handleRefresh={handleRefresh}></AddDoctorForm>
      </Modal>
    </div>
  );
}
