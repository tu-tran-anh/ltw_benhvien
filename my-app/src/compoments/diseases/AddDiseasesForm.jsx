import React, { useState } from "react";
import { Form, Input, Button, Row, Col, DatePicker,Select } from "antd";
import API from "../../callAPI";
// import API from "../callAPI";
const { Option } = Select;
export default function AddDiseasesForm({ sendMessage, data, handleRefresh }) {
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
            const result = await API.CRUD.updateItem(form.getFieldsValue(),"diseases",data.iddiseases);
            handleRefresh();
          } catch (error) {
            sendMessage("fail to update diseases");
          }
        } else {
          try {
            const result = await API.CRUD.addItem(form.getFieldsValue(),"diseases");
            handleRefresh();
          } catch (error) {
            console.log("error: ", error);
            sendMessage("fail to add diseases");
          }
        }
      }}
    >
      <Form.Item
        name="name"
        label="diseases name"
        rules={[{ required: true, message: "please input diseases name" }]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        name="description"
        label="description"
        rules={[{ required: true, message: "please input diseases description" }]}
      >
        <Input></Input>
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
            console.log("field value diseases: ", form.getFieldsValue());
          }}
        >
          Submit
        </Button>
        <Col span={4}></Col>
      </Row>
    </Form>
  );
}
