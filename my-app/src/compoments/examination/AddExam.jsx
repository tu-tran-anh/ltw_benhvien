import React, { useState,useEffect } from "react";
import { Modal} from "antd";
import AddExamForm from "./AddExamForm";

export default function AddExam({ isModalVisible, setIsModalVisible, data, handleRefresh}) {
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
        <AddExamForm sendMessage={sendMessage} data={data} handleRefresh={handleRefresh}></AddExamForm>
      </Modal>
    </div>
  );
}
