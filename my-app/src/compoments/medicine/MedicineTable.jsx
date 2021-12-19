import { Table, Button, Row, Col, message } from "antd";
import API from "../../callAPI";
import React, { useState, useEffect } from "react";
import SearchForm from "../SearchForm";
import AddMedicine from "./AddMedicine";

export default function MedicineTable() {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [dataRow, setDataRow] = useState(null);
  const [datas, setDatas] = useState([]);
  const [isDelete, setIsDelete] = useState(false);
  const [selectRow, setSelectRow] = useState([]);
  const [searchText, setSearchText] = useState("");

  const searchRanger = ["idmedicine", "name", "cost", "type"];

  const handleRefresh = async () => {
    await callData();
  };
  useEffect(async () => {
    await callData();
  }, []);

  const handleSearch = async (searchField) => {
    let mydata, doctor;
    if (searchField == "all") {
      doctor = await API.CRUD.getAll("medicine");
    } else {
      doctor = await API.CRUD.findlikeItem("medicine", searchField, searchText);
    }
    mydata = JSON.parse(doctor.data).map((value) => {
      value.key = "" + value.idmedicine + "d";
      return value;
    });

    setDatas(mydata);
  };

  const callData = async () => {
    try {
      const nurse = await API.CRUD.getAll("medicine");
      console.log("my nurse: ", nurse);
      let mydata = JSON.parse(nurse.data).map((value, index) => {
        value.key = "" + value.idmedicine;
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
      dataIndex: "idmedicine",
    },
    {
      title: "name",
      dataIndex: "name",
    },
    {
      title: "cost",
      dataIndex: "cost",
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
    <div className='mx-auto w-9/12'>
      <div className="my-6">
        <SearchForm
          data={searchRanger}
          handleSearch={handleSearch}
          setSearchText={setSearchText}
        ></SearchForm>
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
          <Button
            type=""
            disabled={!isDelete}
            onClick={async () => {
              let max = selectRow.length,
                min = 0,
                result;
              for (let i = 0; i < selectRow.length; i++) {
                result = await API.CRUD.deleteItem(
                  "medicine",
                  selectRow[i].idmedicine
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

      <AddMedicine
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        data={dataRow}
        handleRefresh={handleRefresh}
      ></AddMedicine>
    </div>
  );
}
