import { Table, Button, Row, Col,message } from "antd";
import API from "../../callAPI";
import React, { useState, useEffect } from "react";
import AddDiseases from "./AddDiseases";
import SearchForm from "../SearchForm";

export default function DiseasesTable() {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [dataRow, setDataRow] = useState(null);
  const [datas, setDatas] = useState([]);
  const [isDelete, setIsDelete] = useState(false);
  const [selectRow, setSelectRow] = useState([]);
  const [searchText, setSearchText] = useState('');

  const searchRanger = [
    "iddiseases",
    "name",
    "description",
    "type",
  ];

  const handleRefresh = async () => {
    await callData();
  };
  useEffect(async () => {
    await callData();
  }, []);

  const handleSearch = async(searchField)=>{
    let mydata, diseases;
    if (searchField == "all") {
      diseases = await API.CRUD.getAll("diseases");
    } else {
      diseases = await API.CRUD.findlikeItem("diseases", searchField, searchText);
    }
    mydata = JSON.parse(diseases.data).map((value, index) => {
      value.key = "" + value.iddiseases;
      return value;
    });

    setDatas(mydata);
  }

  const callData = async () => {
    try {
      const diseases = await API.CRUD.getAll("diseases");
      let mydata = JSON.parse(diseases.data).map((value,index)=>{
        value.key = ''+value.iddiseases;
        return value;
      });
      console.log("data: ",mydata);
      setDatas(mydata)
    } catch (error) {console.log("error: ",error);}
  };

  const columns = [
    {
      title: "id",
      dataIndex: "iddiseases",
    },
    {
      title: "name",
      dataIndex: "name",
    },
    {
      title: "description",
      dataIndex: "description",
    },
    {
      title: "type",
      dataIndex: "type",
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
      const str = JSON.stringify(selectedRows);
      if(JSON.stringify(selectedRows) != JSON.stringify([])){
        setIsDelete(true);
      }else{
        setIsDelete(false);
      }
    },
  };

  return (
    <div className = 'w-9/12 mx-auto'>
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
              result = await API.CRUD.deleteItem("diseases",selectRow[i].iddiseases);
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
      <AddDiseases
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        data={dataRow}
        handleRefresh={handleRefresh}
      ></AddDiseases>
    </div>
  );
}
