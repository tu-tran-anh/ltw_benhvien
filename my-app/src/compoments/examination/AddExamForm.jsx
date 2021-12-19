import React, { useState } from "react";
import {
  Form,
  Input,
  Button,
  Row,
  Col,
  Select,
  InputNumber,
  DatePicker,
} from "antd";
import API from "../../callAPI";

const { Option } = Select;
export default function AddExamForm({ sendMessage, data, handleRefresh }) {
  const [form] = Form.useForm();
  const [diseases, setDiseases] = useState([]);
  const [doctor, setDoctor] = useState([]);
  const [nurse, setNurse] = useState([]);
  const [patient, setPatient] = useState([]);
  React.useEffect(async () => {
    if (data) {
      let doctordiseases = await API.CRUD.findItem("doctordiseases","iddoctordiseases",data.iddoctordiseases);
      doctordiseases = JSON.parse(doctordiseases.data)[0];
      let iddoctor = doctordiseases.iddoctor;
      let iddiseases = doctordiseases.iddiseases;
      let mydoctor = await API.CRUD.findItem("doctor","iddoctor",iddoctor);
      let mydiseases = await API.CRUD.findItem("diseases","iddiseases",iddiseases);
      mydoctor = JSON.parse(mydoctor.data)[0];
      mydiseases = JSON.parse(mydiseases.data)[0];

      let mynurse = await API.CRUD.findItem("nurse","idnurse",data.idnurse);
      mynurse = JSON.parse(mynurse.data)[0];

      let mypatient = await API.CRUD.findItem("patient","idpatient",data.idpatient);
      mypatient = JSON.parse(mypatient.data)[0];

      form.setFieldsValue({
        diseases: mydiseases.iddiseases+":"+mydiseases.name,
        doctor: mydoctor.iddoctor+":"+mydoctor.name,
        nurse: mynurse.idnurse+":"+mynurse.name,
        patient: mypatient.idpatient+":"+mypatient.name,
        dayin : data.dayin
      });
      await callDoctor(form.getFieldValue("diseases"));
    } else {
      form.resetFields();
      form.setFieldsValue({
        type: "not",
      });
    }
    await callData();
  }, [data]);

  const callData = async () => {
    let allDiseases = await API.CRUD.getAll("diseases");
    allDiseases = JSON.parse(allDiseases.data);
    let myData = allDiseases.filter((value) => value.type == "active");
    setDiseases(
      myData.map((value) => {
        return value.iddiseases + ":" + value.name;
      })
    );

    let allNurse = await API.CRUD.getAll("nurse");
    allNurse = JSON.parse(allNurse.data);
    let myDataNurse = allNurse.filter((value) => value.type == "active");
    setNurse(
      myDataNurse.map((value) => {
        return value.idnurse + ":" + value.name;
      })
    );

    let allPatient = await API.CRUD.getAll("patient");
    allPatient = JSON.parse(allPatient.data);
    let myDataPatient = allPatient;
    console.log("my data patient: ", myDataPatient);
    setPatient(
      myDataPatient.map((value) => {
        return value.idpatient + ":" + value.name;
      })
    );
  };

  const callDoctor = async (benh) => {
    let idDiseases = Number(benh.split(":")[0]);
    let allDoctor = await API.CRUD.getDoctorDiseases(idDiseases);
    allDoctor = JSON.parse(allDoctor.data);
    setDoctor(
      allDoctor.map((value) => {
        return value.iddoctor + ":" + value.name;
      })
    );
  };

  return (
    <Form
      form={form}
      layout="horizontal"
      labelAlign="left"
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 16 }}
      size="large"
      onFinish={async () => {
        let iddoctor = Number(form.getFieldValue("doctor").split(":")[0]);
        let iddiseases = Number(form.getFieldValue("diseases").split(":")[0]);
        let myObject = {
          idnurse: Number(form.getFieldValue("nurse").split(":")[0]),
          idpatient: Number(form.getFieldValue("patient").split(":")[0]),
          dayin: form.getFieldValue("dayin"),
          type: "not",
          iddoctordiseases: null,
        };
        let findDoctorDiseases = await API.CRUD.findDoctorDiseases(
          iddoctor,
          iddiseases
        );
        findDoctorDiseases = JSON.parse(findDoctorDiseases.data);
        myObject.iddoctordiseases = findDoctorDiseases[0].iddoctordiseases;
        if (!data) {
          try {
            let result = await API.CRUD.addItem(myObject, "examination");
            console.log("them examination: ", result);
            handleRefresh();
          } catch (error) {
            sendMessage("fail to update patient");
          }
        } else {
          console.log("vao trong ben update");
          try {
            let result = await API.CRUD.updateItem(myObject, "examination",data.idexamination);
            console.log("them examination: ", result);
            handleRefresh();
          } catch (error) {
            sendMessage("fail to update patient");
          }
        }
      }}
    >
      <Form.Item
        name="diseases"
        label="diseases"
        rules={[{ required: true, message: "please input your diseases" }]}
      >
        <Select
          defaultValue=""
          className="w-full"
          onChange={async (e) => {
            callDoctor(e);
            form.setFieldsValue({
              doctor: "",
            });
          }}
        >
          <Option key=""></Option>
          {diseases
            ? diseases.map((value) => {
                return <Option key={value}>{value}</Option>;
              })
            : null}
        </Select>
      </Form.Item>
      <Form.Item
        name="doctor"
        label="doctor"
        rules={[{ required: true, message: "please input your diseases" }]}
      >
        <Select defaultValue="" className="w-full">
          <Option key=""></Option>
          {doctor
            ? doctor.map((value) => {
                return <Option key={value}>{value}</Option>;
              })
            : null}
        </Select>
      </Form.Item>
      <Form.Item
        name="nurse"
        label="nurse"
        rules={[{ required: true, message: "please input nurse author" }]}
      >
        <Select defaultValue="" className="w-full">
          <Option key=""></Option>
          {nurse
            ? nurse.map((value) => {
                return <Option key={value}>{value}</Option>;
              })
            : null}
        </Select>
      </Form.Item>
      <Form.Item
        name="patient"
        label="patient"
        rules={[{ required: true, message: "please input nurse author" }]}
      >
        <Select defaultValue="" className="w-full">
          <Option key=""></Option>
          {patient
            ? patient.map((value) => {
                return <Option key={value}>{value}</Option>;
              })
            : null}
        </Select>
      </Form.Item>
      <Row>
        <Col span={24}>
          <Form.Item
            name="dayin"
            label="dayin"
            rules={[{ required: true, message: "please input doctor author" }]}
          >
            <Input disabled={true}></Input>
          </Form.Item>
        </Col>
        <Col span={4} offset={4}>
          <DatePicker
            format={"DD/MM/YYYY"}
            onChange={(e) => {
              try {
                form.setFieldsValue({
                  dayin: e.format("DD/MM/YYYY"),
                });
              } catch (error) {
                form.setFieldsValue({
                  dayin: "",
                });
              }
            }}
          />
        </Col>
      </Row>

      <Row>
        <Button
          type="primary"
          htmlType="submit"
          className="ml-4"
          onClick={async () => {}}
        >
          Submit
        </Button>
        <Col span={4}></Col>
      </Row>
    </Form>
  );
}
