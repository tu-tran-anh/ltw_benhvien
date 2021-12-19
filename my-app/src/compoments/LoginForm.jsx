import React, { useState, useContext } from "react";
import { Form, Input, Button, Row, Col,message } from "antd";
import API from "../callAPI";
import { MainContext } from "../context";
import { Link } from "react-router-dom";

export default function LoginForm() {
  console.log("vao login form");
  const { setIsLogin } = useContext(MainContext);
  const [form] = Form.useForm();
  return (
    <Form
      form={form}
      layout="horizontal"
      labelAlign="left"
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 16 }}
      size="large"
      onFinish={async () => {
        console.log("user mail: ", form.getFieldsValue());
        let result = await API.Login.signin(form.getFieldsValue());
        console.log("result login: ",result);
        if (result.status == 200) {
          localStorage.setItem("token", result.token);
          let myUser = await API.Login.getCurrentUser();
          if (myUser.data.kindPerson != "admin") {
            setIsLogin(true);
          }else{
            message.info('không đủ thẩm quyền !!');
          }
        }
      }}
    >
      <Form.Item
        name="email"
        label="email"
        rules={[{ required: true, message: "please input your email" }]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        name="password"
        label="password"
        rules={[{ required: true, message: "please input your password" }]}
      >
        <Input.Password></Input.Password>
      </Form.Item>
      <Row>
        <Col>
          <Button type="primary" htmlType="submit">
            Login
          </Button>
        </Col>
        <Col span={11} offset={8}>
          <Button type="ghost">
            <Link to="/forgotpass">forget your password?</Link>
          </Button>
        </Col>
      </Row>
    </Form>
  );
}
