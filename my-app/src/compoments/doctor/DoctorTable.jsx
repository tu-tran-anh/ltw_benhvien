import { Table, Button, Row, Col, message } from "antd";
import styled from "styled-components";
import API from "../../callAPI";
import React, { useState, useEffect } from "react";
import AddDotor from "./AddDoctor";
import SearchForm from "../SearchForm";
import DoctorAndDiseases from "./DoctorAndDiseases";
import Salary from "./Salary";

const MyTable = styled.div`
  .ant-table-cell {
    img {
      height: 100px;
      width: 100px;
    }
  }
`;

export default function DoctorTable() {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isSalaryVisible, setIsSalaryVisible] = useState(false);
  const [doctordiseasesVisible, setDoctordiseasesVisible] = useState(false);

  const [dataRow, setDataRow] = useState(null);
  const [datas, setDatas] = useState([]);
  const [isDelete, setIsDelete] = useState(false);
  const [selectRow, setSelectRow] = useState([]);
  const [searchText, setSearchText] = useState("");

  const searchRanger = [
    "iddoctor",
    "name",
    "birthday",
    "cmd",
    "address",
    "seniority",
    "type",
  ];

  const handleRefresh = async () => {
    await callData();
  };
  useEffect(async () => {
    await callData();
  }, []);

  const handleSearch = async (searchField) => {
    let mydata, doctor;
    if(searchField == 'all'){
      doctor = await API.CRUD.getAll("doctor");
    }else{
      doctor = await API.CRUD.findlikeItem("doctor",searchField,searchText);
    }
    mydata = JSON.parse(doctor.data).map((value) => {
      value.key = "" + value.iddoctor + "d";
      return value;
    });

    setDatas(mydata);
  };

  const callData = async () => {
    try {
      const doctor = await API.CRUD.getAll("doctor");

      let mydata = JSON.parse(doctor.data).map((value, index) => {
        value.key = "" + value.iddoctor + "d";
        return value;
      });

      setDatas(mydata);
    } catch (error) {
      console.log("error: ", error);
    }
  };

  const columns = [
    {
      title: "id",
      dataIndex: "iddoctor",
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
      title: "level",
      dataIndex: "level",
    },
    {
      title: "seniority",
      dataIndex: "seniority",
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
    {
      title: "diseases",
      dataIndex: "diseases",
      render: (_, row) => (
        <p
          onClick={() => {
            setDoctordiseasesVisible(true);
            setDataRow(row);
          }}
        >
          set diseases
        </p>
      ),
    },
    {
      title: "salary",
      dataIndex: "salary",
      render: (_, row) => (
        <p
          onClick={() => {
            console.log("row ", row);
            setIsSalaryVisible(true);
            setDataRow(row);
          }}
        >
          salary
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
      if (JSON.stringify(selectedRows) != JSON.stringify([])) {
        setIsDelete(true);
      } else {
        setIsDelete(false);
      }
    },
  };

  return (
    <MyTable className="w-9/12 mx-auto">
      <div className="my-6">
        <SearchForm
          data={searchRanger}
          handleSearch={handleSearch}
          setSearchText={setSearchText}
        ></SearchForm>
      </div>
      <Row className="mb-4">
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
          <Button
            type=""
            disabled={!isDelete}
            onClick={async () => {
              let max = selectRow.length,
                min = 0,
                result;
              for (let i = 0; i < selectRow.length; i++) {
                result = await API.CRUD.deleteItem(
                  "doctor",
                  selectRow[i].iddoctor
                );
                if (result.status == 200 || result.status == "200") {
                  min++;
                }
              }
              setSelectRow([]);
              message.success(`delete result: ${min}/${max}`, 3);
              await callData();
            }}
          >
            Delete
          </Button>
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
      <AddDotor
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        data={dataRow}
        handleRefresh={handleRefresh}
      ></AddDotor>
      <DoctorAndDiseases
        isModalVisible={doctordiseasesVisible}
        setIsModalVisible={setDoctordiseasesVisible}
        data={dataRow}
      ></DoctorAndDiseases>
      <Salary
        isModalVisible={isSalaryVisible}
        setIsModalVisible={setIsSalaryVisible}
        data={dataRow}
      ></Salary>
    </MyTable>
  );
}
