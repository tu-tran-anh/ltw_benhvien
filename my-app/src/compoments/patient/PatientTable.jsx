import { Table, Button, Row, Col,message } from "antd";
import API from "../../callAPI";
import React, { useState, useEffect } from "react";
import AddPatient from "./AddPatient";
import SearchForm from "../SearchForm";

export default function PatientTable() {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [dataRow, setDataRow] = useState(null);
  const [datas, setDatas] = useState([]);
  const [isDelete, setIsDelete] = useState(false);
  const [selectRow, setSelectRow] = useState([]);
  const [searchText, setSearchText] = useState('');

  const searchRanger = [
    "idpatient",
    "name",
    "birthday",
    "cmd",
    "address",
  ];

  const handleRefresh = async () => {
    await callData();
  };
  useEffect(async () => {
    await callData();
  }, []);

  const handleSearch = async(searchField)=>{
    let mydata, patient;
    if(searchField == 'all'){
      patient = await API.CRUD.getAll("patient");
    }else{
      patient = await API.CRUD.findlikeItem("patient",searchField,searchText);
    }
    mydata = JSON.parse(patient.data).map((value,index)=>{
      value.key = ''+value.idpatient;
      return value;
    });

    setDatas(mydata)
  }

  const callData = async () => {
    try {
      const nurse = await API.CRUD.getAll("patient");
      console.log("my nurse: ",nurse);
      let mydata = JSON.parse(nurse.data).map((value,index)=>{
        value.key = ''+value.idpatient;
        return value;
      });

      setDatas(mydata)
    } catch (error) {console.log("error: ",error);}
  };

  const columns = [
    {
      title: "id",
      dataIndex: "idpatient",
    },
    {
      title: "name",
      dataIndex: "name",
    },
    {
      title: "birthday",
      dataIndex: "birthday",
    },
    {
      title: "cmd",
      dataIndex: "cmd",
    },
    {
      title: "address",
      dataIndex: "address",
    },
    {
      title: "phone",
      dataIndex: "phone",
    },
    {
      title: "action",
      dataIndex: "action",
      render: (_, row) => (
        <p
          onClick={() => {
            console.log("row ", row);
            setIsModalVisible(true);
            setDataRow(row);
          }}
        >
          edit
        </p>
      ),
    },
  ];

  const rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      console.log(
        `selectedRowKeys: ${selectedRowKeys}`,
        "selectedRows: ",
        selectedRows
      );
      setSelectRow(selectedRows);
      if(JSON.stringify(selectedRows) != JSON.stringify([])){
        setIsDelete(true);
      }else{
        setIsDelete(false);
      }
    },
  };

  return (
    <div className='w-9/12 mx-auto'>
      <div className='my-6'>
      <SearchForm data={searchRanger} handleSearch={handleSearch} setSearchText={setSearchText}></SearchForm>
      </div>
      <Row className='mb-4'>
        <Col span={4}>
          <Button
            onClick={(e) => {
              setIsModalVisible(true);
              setDataRow(null);
            }}
          >
            {"add"}
          </Button>
        </Col>
        <Col span={4} offset={4}>
          <Button type="" disabled={!isDelete} onClick={async ()=>{
            let max = selectRow.length, min = 0,result;
            for (let i = 0; i < selectRow.length; i++) {
              result = await API.CRUD.deleteItem("patient",selectRow[i].idpatient);
              if(result.status == 200 || result.status == '200'){
                min++;
              }
            }
            setSelectRow([]);
            message.success(`delete result: ${min}/${max}`, 3);
            await callData();
          }}>Delete</Button>
        </Col>
      </Row>
      <Table
        columns={columns}
        dataSource={datas}
        rowSelection={{
          type: "checkbox",
          ...rowSelection,
        }}
      ></Table>
      
      <AddPatient
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        data={dataRow}
        handleRefresh={handleRefresh}
      ></AddPatient>
    </div>
  );
}
