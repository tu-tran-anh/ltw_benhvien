import { Table, Button, Row, Col, message } from "antd";
import API from "../../callAPI";
import React, { useState, useEffect } from "react";
import AddNurse from "./AddNurse";
import SearchForm from "../SearchForm";

export default function NurseTable() {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [dataRow, setDataRow] = useState(null);
  const [datas, setDatas] = useState([]);
  const [isDelete, setIsDelete] = useState(false);
  const [selectRow, setSelectRow] = useState([]);
  const [searchText, setSearchText] = useState("");

  const handleRefresh = async () => {
    await callData();
  };
  useEffect(async () => {
    await callData();
  }, []);

  const searchRanger = [
    "idnurse",
    "name",
    "birthday",
    "cmd",
    "address",
    "phone",
    "seniority",
    "type",
  ];

  const handleSearch = async (searchField) => {
    let mydata, nurse;
    if (searchField == "all") {
      nurse = await API.CRUD.getAll("nurse");
    } else {
      nurse = await API.CRUD.findlikeItem("nurse", searchField, searchText);
    }
    mydata = JSON.parse(nurse.data).map((value, index) => {
      value.key = "" + value.idnurse;
      return value;
    });

    setDatas(mydata);
  };

  const callData = async () => {
    try {
      const nurse = await API.CRUD.getAll("nurse");
      console.log("my nurse: ", nurse);
      let mydata = JSON.parse(nurse.data).map((value, index) => {
        value.key = "" + value.idnurse;
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
      dataIndex: "idnurse",
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
                  "nurse",
                  selectRow[i].idnurse
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

      <AddNurse
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        data={dataRow}
        handleRefresh={handleRefresh}
      ></AddNurse>
    </div>
  );
}
