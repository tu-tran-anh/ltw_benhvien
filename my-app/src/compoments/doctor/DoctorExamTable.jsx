import { Table,Modal, } from "antd";
import React, { useState, useEffect } from "react";
import ExamDetail from "../examination/ExamDetail";

export default function DoctorExamTable({data,isModalVisible,setIsModalVisible}) {
  const [isDetailVisible, setIsDetailVisible] = useState(false);

  const [dataRow, setDataRow] = useState(null);

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
        title: "setSalary",
        dataIndex: "setSalary",
      },
    {
      title: "type",
      dataIndex: "type",
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

  return (
    <div className="w-9/12 mx-auto">
      
      <Modal
        visible={isModalVisible}
        onCancel={() => {
          setIsModalVisible(false);
        }}
        width = {1000}
        footer={[
          <p></p>
        ]}
      >
        <Table
        columns={columns}
        dataSource={data?data:[]}
      ></Table>
      <ExamDetail
        isModalVisible={isDetailVisible}
        setIsModalVisible={setIsDetailVisible}
        data={dataRow}
      ></ExamDetail>
      </Modal>
    </div>
  );
}
