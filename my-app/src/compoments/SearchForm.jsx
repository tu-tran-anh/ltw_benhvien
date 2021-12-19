import React, { useState, useContext } from "react";
import { Form, Input, Button, Search, Col, Row, Select } from "antd";
import API from "../callAPI";
import { MainContext } from "../context";
import { SearchOutlined } from "@ant-design/icons";
const { Option } = Select;

export default function SearchForm({ data, handleSearch, setSearchText }) {
  const [type, setType] = React.useState("all");
  const [form] = Form.useForm();
  return (
    <Row>
      <Col span={2}>
        <Select defaultValue="all" className="w-full" onChange={(e)=>{
          setType(e);
        }}>
          <Option key="all">all</Option>
          {data ? data.map((value)=>{
            return <Option key={value}>{value}</Option>
          }):null}
        </Select>
      </Col>
      <Col span={8} offset={1}>
        <Input.Search
          prefix={<SearchOutlined />}
          placeholder="input search text"
          onSearch={()=>{
            handleSearch(type);
          }}
          enterButton
          onChange={(e) => {
            setSearchText(e.target.value);
          }}
        />
      </Col>
    </Row>
  );
}
