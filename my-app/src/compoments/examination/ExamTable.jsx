import { Table, Button, Row, Col, message, DatePicker } from "antd";
import API from "../../callAPI";
import React, { useState, useEffect } from "react";
import SearchForm from "../SearchForm";
import AddExam from "./AddExam";
import ExamCheckOut from "./ExamCheckOut";
import ExamDetail from "./ExamDetail";

export default function ExamTable() {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isCheckOutVisible, setIsCheckOutVisible] = useState(false);
  const [isDetailVisible, setIsDetailVisible] = useState(false);

  const [dataRow, setDataRow] = useState(null);
  const [datas, setDatas] = useState([]);
  const [isDelete, setIsDelete] = useState(false);
  const [selectRow, setSelectRow] = useState([]);
  const [searchText, setSearchText] = useState("");

  const searchRanger = ["idexamination", "dayin", "dayout", "type"];

  const handleRefresh = async () => {
    await callData();
  };
  useEffect(async () => {
    await callData();
  }, []);

  const handleSearch = async (searchField) => {
    let mydata, doctor;
    if (searchField == "all") {
      doctor = await API.CRUD.getAll("examination");
    } else {
      doctor = await API.CRUD.findlikeItem(
        "examination",
        searchField,
        searchText
      );
    }
    mydata = JSON.parse(doctor.data).map((value) => {
      value.key = "" + value.idexamination + "d";
      return value;
    });

    setDatas(mydata);
  };

  const callData = async () => {
    try {
      const nurse = await API.CRUD.getAll("examination");
      console.log("my nurse: ", nurse);
      let mydata = JSON.parse(nurse.data).map((value, index) => {
        value.key = "" + value.idexamination;
        return value;
      });
      console.log("data: ", mydata);
      setDatas(mydata);
    } catch (error) {
      console.log("error: ", error);
    }
  };

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
      title: "checkout",
      dataIndex: "checkout",
      render: (_, row) => (
        <p
          onClick={() => {
            console.log("row ", row);
            if (row.type != "payed") {
              setIsCheckOutVisible(true);
              setDataRow(row);
            }
          }}
        >
          check
        </p>
      ),
    },
    {
      title: "details",
      dataIndex: "details",
      render: (_, row) => (
        <p
          onClick={() => {
            console.log("row ", row);
            setIsDetailVisible(true);
            setDataRow(row);
          }}
        >
          details
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
    <div className="w-9/12 mx-auto">
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
                  "examination",
                  selectRow[i].idexamination
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

      <AddExam
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        data={dataRow}
        handleRefresh={handleRefresh}
      ></AddExam>
      <ExamCheckOut
        isModalVisible={isCheckOutVisible}
        setIsModalVisible={setIsCheckOutVisible}
        data={dataRow}
        handleRefresh={handleRefresh}
      ></ExamCheckOut>
      <ExamDetail
        isModalVisible={isDetailVisible}
        setIsModalVisible={setIsDetailVisible}
        data={dataRow}
      ></ExamDetail>
    </div>
  );
}
