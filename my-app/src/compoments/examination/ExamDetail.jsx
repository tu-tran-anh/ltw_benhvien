import { Modal } from "antd";
import React, { useState } from "react";
import API from "../../callAPI";
import Detail from "./Detail";

export default function ExamDetail({
  isModalVisible,
  setIsModalVisible,
  data,
}) {
  const [patient, setPatient] = useState({});
  const [nurse, setNurse] = useState({});
  const [doctor, setDoctor] = useState({});
  const [diseases, setDiseases] = useState({});

  React.useEffect(async () => {
    if (data) {
      console.log("exam detail data: ", data);
      let patient = await API.CRUD.findItem(
        "patient",
        "idpatient",
        data.idpatient
      );
      patient = JSON.parse(patient.data)[0];
      setPatient(patient);

      let nurse = await API.CRUD.findItem("nurse", "idnurse", data.idnurse);
      nurse = JSON.parse(nurse.data)[0];
      setNurse(nurse);

      let doctordiseases = await API.CRUD.findItem("doctordiseases", "iddoctordiseases", data.iddoctordiseases);
      doctordiseases = JSON.parse(doctordiseases.data)[0];

      let doctor = await API.CRUD.findItem("doctor", "iddoctor", doctordiseases.iddoctor);
      doctor = JSON.parse(doctor.data)[0];
      setDoctor(doctor);

      let diseases = await API.CRUD.findItem("diseases", "iddiseases", doctordiseases.iddiseases);
      diseases = JSON.parse(diseases.data)[0];
      setDiseases(diseases);
    }
  }, [data]);
  return (
    <div>
      <Modal
        visible={isModalVisible}
        // onOk={()=>{handleChange(setIsModalVisible,false)}}
        onCancel={() => {
          setIsModalVisible(false);
        }}
        width={800}
        footer={[<p>{""}</p>]}
      >
        <Detail data={patient} title={"PATIENT"}></Detail>
        <Detail data={nurse} title={"NURSE"}></Detail>
        <Detail data={doctor} title={"DOCTOR"}></Detail>
        <Detail data={diseases} title={"DISEASES"}></Detail>
      </Modal>
    </div>
  );
}
