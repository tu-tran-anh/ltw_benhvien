import React, { useState } from "react";
import { Form, Input, Button, Row, Col,Select, InputNumber } from "antd";
import API from "../../callAPI";
// import API from "../callAPI";
const { Option } = Select;
export default function AddMedicineForm({ sendMessage, data, handleRefresh }) {
  const [form] = Form.useForm();
  React.useEffect(() => {
    if (data) {
      form.setFieldsValue(data);
    } else {
      form.resetFields();
      form.setFieldsValue({
        type: "active",
      });
    }
  }, [data]);
  // const [date, setPassword] = useState('');
  // const user = await API.Login.getCurrentUser().data;
  // console.log(user);

  return (
    <Form
      form={form}
      layout="horizontal"
      labelAlign="left"
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 16 }}
      size="large"
      onFinish={async () => {
        if (data) {
          try {
            // console.log("update nurse");
            const result = await API.CRUD.updateItem(
              form.getFieldsValue(),
              "medicine",
              data.idmedicine
            );
            // console.log("result update nurse: ",result);
            handleRefresh();
          } catch (error) {
            sendMessage("fail to update patient");
          }
        } else {
          try {
            const result = await API.CRUD.addItem(
              form.getFieldsValue(),
              "medicine"
            );
            // console.log("result add doctor: ",result);
            handleRefresh();
          } catch (error) {
            console.log("error: ", error);
            sendMessage("fail to add patient");
          }
        }
      }}
    >
      <Form.Item
        name="name"
        label="medicine name"
        rules={[{ required: true, message: "please input your nurse name" }]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        name="cost"
        label="cost"
        rules={[{ required: true, message: "please input nurse author" }]}
      >
        <InputNumber></InputNumber>
      </Form.Item>
      <Form.Item name="type" label="type">
        <Select defaultValue="active">
          <Option key="active">active</Option>
          <Option key="not">not</Option>
        </Select>
      </Form.Item>

      <Row>
        <Button
          type="primary"
          htmlType="submit"
          className="ml-4"
          onClick={() => {
            console.log("field value doctor: ", form.getFieldsValue());
          }}
        >
          Submit
        </Button>
        <Col span={4}></Col>
      </Row>
    </Form>
  );
}
