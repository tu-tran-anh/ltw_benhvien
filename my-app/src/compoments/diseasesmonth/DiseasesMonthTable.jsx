import React, { useState, useEffect } from "react";
import { Modal, Button, DatePicker, Row, Col, message,Table } from "antd";
import API from "../../callAPI";

export default function DiseasesMonth({}) {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [data, setData] = useState([]);
  const [dataDiseases, setDataDiseases] = useState([]);
  const [month, setMonth] = useState("");
  const [dataRow, setDataRow] = useState([]);

  const countDiseases = async () => {
    if (month == "") {
      message.info("please input the month");
      return 0;
    }
    let myExamination = await API.CRUD.findlikeItem(
      "examination",
      "dayin",
      month
    );
    myExamination = JSON.parse(myExamination.data);

    myExamination.sort((a, b) => {
      return Number(a.dayin.split("/")[0]) - Number(b.dayin.split("/")[0]);
    });
    setData(myExamination);
    console.log("diseases month: ", myExamination);
    let lstData = [];

    let mydoctordiseases = await API.CRUD.findItem("doctordiseases","iddoctordiseases",myExamination[0].iddoctordiseases);
    mydoctordiseases = JSON.parse(mydoctordiseases.data)[0];
    let mydiseases = await API.CRUD.findItem("diseases","iddiseases",mydoctordiseases.iddiseases);
    mydiseases = JSON.parse(mydiseases.data)[0];

    console.log("mydoctordiseases: ",mydoctordiseases);
    console.log("mydiseases: ",mydiseases);
    let find;
    for(let i = 0; i<myExamination.length; i++){
      mydoctordiseases = await API.CRUD.findItem("doctordiseases","iddoctordiseases",myExamination[i].iddoctordiseases);
      mydoctordiseases = JSON.parse(mydoctordiseases.data)[0];
      mydiseases = await API.CRUD.findItem("diseases","iddiseases",mydoctordiseases.iddiseases);
      mydiseases = JSON.parse(mydiseases.data)[0];

      find = findDiseases(lstData,mydiseases.iddiseases);
      console.log("my find: ",find);
      if(find != -1){
        console.log("khng tao moi");
        lstData[find].count++;
        lstData[find].examinations.push(myExamination[i]);
      }else{
        console.log("tao moi");
        lstData.push(mydiseases);
        lstData[lstData.length-1].count = 1;
        lstData[lstData.length-1].examinations = [myExamination[i]];
      }
      console.log(lstData);
      setDataDiseases(lstData.map((value)=>{
        value.key = value.iddiseases;
        return value;
      }));
    }
  };

  const findDiseases = (list,iddiseases) =>{
    for(let i = 0; i<list.length;i++){
      if(list[i].iddiseases == iddiseases){
        return i;
      }
    }return -1;
  }

  useEffect(async () => {
    console.log("data vao trong ");
  }, [data]);

  const columns1 = [
    {
      title: "id",
      dataIndex: "iddiseases",
      key:"iddiseases"
    },
    {
      title: "name",
      dataIndex: "name",
      key:"name"
    },
    {
      title: "description",
      dataIndex: "description",
      key:"description"
    },
    {
      title: "type",
      dataIndex: "type",
      key:"type"
    },
    {
      title: "count",
      dataIndex: "count",
      key:"count"
    },
    {
      title: "action",
      dataIndex: "action",
      key:"action",
      render: (_, row) => (
        <p
          onClick={() => {
            console.log("row ", row);
            setIsModalVisible(true);
            setDataRow(row);
          }}
        >
          detail
        </p>
      ),
    },
  ];

  const columns = [
    {
      title: "id",
      dataIndex: "idexamination",
    },
    {
      title: "iddoctordiseases",
      dataIndex: "iddoctordiseases",
    },
    {
      title: "idnurse",
      dataIndex: "idnurse",
    },
    {
      title: "idpatient",
      dataIndex: "idpatient",
    },
    {
      title: "dayin",
      dataIndex: "dayin",
    },
    {
      title: "dayout",
      dataIndex: "dayout",
    },
    {
      title: "type",
      dataIndex: "type",
    },
  ];

  return (
    <div className="mx-auto ">
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
            format={"M/YYYY"}
          />
        </Col>
      </Row>
      <Row className="my-4">
        <Button
          onClick={async () => {
            await countDiseases();
          }}
        >
          submit
        </Button>
        <Button
          onClick={async () => {
            console.log("data: ",dataDiseases);
          }}
        >
          xem data
        </Button>
      </Row>
      {/* <Table columns={columns} dataSource={data ? data : []}></Table> */}
      <Table columns={columns1} dataSource={dataDiseases}></Table>
      <div>
      <Modal
        visible={isModalVisible}
        // onOk={()=>{handleChange(setIsModalVisible,false)}}
        onCancel={() => {
          setIsModalVisible(false);
        }}
        width = {800}
        footer={[
          <p></p>
        ]}
      >
        <Table columns={columns} dataSource={dataRow ? dataRow.examinations : []}></Table>
      </Modal>
      </div>
    </div>
  );
}
