import React, { useState } from "react";
import { Form, Input, Button, Row, Select, InputNumber, Col,DatePicker} from "antd";
import API from "../../callAPI";
// import API from "../callAPI";
const { Option } = Select;

export default function AddNurseForm({ sendMessage, data, handleRefresh }) {
  const [form] = Form.useForm();
  React.useEffect(() => {
    if (data) {
      form.setFieldsValue(data);
    } else {
      form.resetFields();
      form.setFieldsValue({
        type: "active",
        level: "daihoc",
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
            console.log("update nurse");
            const result = await API.CRUD.updateItem(form.getFieldsValue(),"nurse",data.idnurse);
            console.log("result update nurse: ",result);
            handleRefresh();
          } catch (error) {
            sendMessage("fail to update doctor");
          }
        } else {
          try {
            const result = await API.CRUD.addItem(form.getFieldsValue(),"nurse");
            // console.log("result add doctor: ",result);
            handleRefresh();
          } catch (error) {
            console.log("error: ",error);
            sendMessage("fail to add doctor");
          }
        }
      }}
    >
      <Form.Item
        name="name"
        label="nurse name"
        rules={[{ required: true, message: "please input your nurse name" }]}
      >
        <Input></Input>
      </Form.Item>
      <Row>
        <Col span={24}>
          <Form.Item
            name="birthday"
            label="birthday"
            rules={[{ required: true, message: "please input nurse author" }]}
          >
            <Input disabled={true}></Input>
          </Form.Item>
        </Col>
        <Col span={4} offset={4}>
          <DatePicker format={"DD/MM/YYYY"} onChange={(e)=>{
            try {
              form.setFieldsValue({
                birthday:e.format("DD/MM/YYYY")
              })
            } catch (error) {
              form.setFieldsValue({
                birthday: ""
              })
            }
          }} />
        </Col>
      </Row>
      <Form.Item
        name="cmd"
        label="cmd"
        rules={[{ required: true, message: "please input nurse cmd" }]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        name="address"
        label="address"
        rules={[{ required: true, message: "please input nurse address" }]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        name="phone"
        label="phone"
        rules={[{ required: true, message: "please input nurse phone" }]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        name="seniority"
        label="seniority"
        rules={[{ required: true, message: "please input nurse seniority" }]}
      >
        <InputNumber></InputNumber>
      </Form.Item>
      <Form.Item name="level" label="level">
        <Select defaultValue="daihoc">
          <Option key="daihoc">daihoc</Option>
          <Option key="caodang">caodang</Option>
          <Option key="trungcap">trungcap</Option>
        </Select>
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
