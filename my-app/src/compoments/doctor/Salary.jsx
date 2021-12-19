import React, { useState, useEffect } from "react";
import { Modal, Button, DatePicker, Row, Col, message } from "antd";
import API from "../../callAPI";
import DoctorExamTable from "./DoctorExamTable";

export default function Salary({ isModalVisible, setIsModalVisible, data }) {
  // const [isModalVisible, setIsModalVisible] = useState(false);
  const [month, setMonth] = useState("");
  const [examination, setExamination] = useState([]);
  const [isDoctorExamVisible, setIsDoctorExamVisible] = useState(false);
  const [doctorSalary, setDoctorSalary] = useState(0);

  const countSalary = async () => {
    if (month == "") {
      message.info("please input the month");
      return 0;
    }
    let myExamination = await API.CRUD.getExamOfDoctor(data.iddoctor, month);
    myExamination = JSON.parse(myExamination.data);

    myExamination.sort((a, b) => {
      return Number(a.dayin.split("/")[0]) - Number(b.dayin.split("/")[0]);
    });
    let salary = {};
    let count = 0;
    for (let i = 0; i < myExamination.length; i++) {
      if (!salary[myExamination[i].idpatient]) {
        count++;
        salary[myExamination[i].idpatient] = myExamination[i].iddoctordiseases;
        myExamination[i].setSalary = "true";
      } else {
        if (
          salary[myExamination[i].idpatient] !=
          myExamination[i].iddoctordiseases
        ) {
          count++;
          salary[myExamination[i].idpatient] =
            myExamination[i].iddoctordiseases;
          myExamination[i].setSalary = "true";
        }
      }
    }
    setExamination(myExamination);
    setDoctorSalary(7000000 + 1000000 * count);
  };

  useEffect(async () => {
    console.log("data vao trong ");
  }, [data]);

  return (
    <div>
      <Modal
        visible={isModalVisible}
        // onOk={()=>{handleChange(setIsModalVisible,false)}}
        onCancel={() => {
          setIsModalVisible(false);
        }}
        width={1000}
        footer={[<p></p>]}
      >
        <Row className="my-4">
          <Col span={2}>Month </Col>
          <Col span={4} offset={1}>
            <DatePicker
              onChange={(e) => {
                try {
                  setMonth(e.format("MM/YYYY"));
                } catch (error) {
                  setMonth("");
                }
              }}
              picker="month"
              format={"MM/YYYY"}
            />
          </Col>
        </Row>
        <Row className="my-4">Doctor Salary: {doctorSalary}</Row>
        <Row className="my-4">
          <Button
            onClick={async () => {
              await countSalary();
            }}
          >
            submit
          </Button>
        </Row>
        <Row className="my-4">
          <Button
            onClick={() => {
              setIsDoctorExamVisible(true);
            }}
          >
            view detail
          </Button>
        </Row>
        <DoctorExamTable
          isModalVisible={isDoctorExamVisible}
          setIsModalVisible={setIsDoctorExamVisible}
          data={examination}
        ></DoctorExamTable>
      </Modal>
    </div>
  );
}