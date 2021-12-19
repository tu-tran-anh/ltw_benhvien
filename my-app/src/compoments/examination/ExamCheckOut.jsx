import React, { useState, useEffect } from "react";
import {
  Modal,
  Checkbox,
  Divider,
  Button,
  Row,
  Col,
  Form,
  message,
  DatePicker,
} from "antd";
import API from "../../callAPI";

const CheckboxGroup = Checkbox.Group;
export default function ExamCheckOut({
  isModalVisible,
  setIsModalVisible,
  data,
  handleRefresh
}) {
  const [medicine, setMedicine] = useState([]);
  const [dayout, setDayout] = useState("");
  const [form] = Form.useForm();
  useEffect(async () => {
    await callData();
    setCheckedList([]);
  }, [isModalVisible]);

  const checkDay = () => {
    let mydayin = data.dayin.split("/").map((value) => {
      return Number(value);
    });

    let mydayout = dayout.split("/").map((value) => {
      return Number(value);
    });

    for (let i = 2; i >= 0; i--) {
      if (mydayout[i] < mydayin[i]) {
        return false;
      }
      if (mydayout[i] > mydayin[i]) {
        return true;
      }
    }
    return true;
  };

  const submit = async () => {
    if (dayout == "") {
      message.info("you must input the day out", 3);
      return 0;
    }else if(!checkDay()){
        message.info("day out must later", 3);
      return 0;
    }
    let myList = checkedList.map((value) => {
      return Number(value.split(":")[0]);
    });

    console.log("id exam: ", data);
    for (let i = 0; i < myList.length; i++) {
      await API.CRUD.addItem({
          idexamination:data.idexamination,
          idmedicine:myList[i]
      },"medicineperexam");
    }

    let myData = { ...data, dayout, type: "payed" };
    delete myData.key;
    let result = await API.CRUD.updateItem(myData,"examination",data.idexamination);
    message.success("success to checkout this examination", 3);
    setIsModalVisible(false);
    handleRefresh();
  };

  const callData = async () => {
    if (!data) {
      return 0;
    }
    let allMedicine = await API.CRUD.getAll("medicine");
    if (allMedicine.status == 200) {
      allMedicine = JSON.parse(allMedicine.data);
      let mydata = allMedicine.map((value) => {
        if (value.type == "active") {
          return value.idmedicine + ":" + value.name;
        } else {
          return null;
        }
      });
      mydata = mydata.filter((value) => value != null);
      setMedicine(mydata);
    }
  };

  const [checkedList, setCheckedList] = React.useState([]);
  const [indeterminate, setIndeterminate] = React.useState(true);
  const [checkAll, setCheckAll] = React.useState(false);

  const onChange = (list) => {
    setCheckedList(list);
    setIndeterminate(!!list.length && list.length < medicine.length);
    setCheckAll(list.length === medicine.length);
  };

  const onCheckAllChange = (e) => {
    setCheckedList(e.target.checked ? medicine : []);
    setIndeterminate(false);
    setCheckAll(e.target.checked);
  };

  return (
    <div>
      <Modal
        visible={isModalVisible}
        onCancel={() => {
          setIsModalVisible(false);
        }}
        width={800}
        footer={[<p>{""}</p>]}
      >
        <Row className="my-4">
          <Col span={2}>
            <p>Day in: </p>
          </Col>
          <Col span={6} offset={1}>
            {data ?data.dayin ? data.dayin:null :null}
          </Col>
        </Row>
        <Row className="my-4">
          <Col span={2}>
            <p>Day out: </p>
          </Col>
          <Col span={6} offset={1}>
            <DatePicker
              format={"DD/MM/YYYY"}
              onChange={(e) => {
                try {
                  setDayout(e.format("DD/MM/YYYY"));
                } catch (error) {
                  setDayout("");
                }
              }}
            />
          </Col>
        </Row>
        <hr></hr>
        <Row className="my-4 text-blue-700 text-xl mt-6">Select Medicine</Row>
        <Checkbox
          indeterminate={indeterminate}
          onChange={onCheckAllChange}
          checked={checkAll}
        >
          Check all
        </Checkbox>
        <Divider />
        <CheckboxGroup
          options={medicine}
          value={checkedList}
          onChange={onChange}
        />
        <hr className="mt-6"></hr>
        <Row>
          <Col span={4}>
            <Button
              className="my-4"
              onClick={async () => {
                console.log("selected box: ", checkedList);
                await submit();
              }}
            >
              submit
            </Button>
          </Col>
          <Col span={4} offset={2}>
            <Button
              className="my-4"
              onClick={async () => {
                setCheckedList([]);
              }}
            >
              refresh
            </Button>
          </Col>
        </Row>
      </Modal>
    </div>
  );
}
