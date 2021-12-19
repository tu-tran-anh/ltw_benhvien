import React, { useContext, useEffect, useState } from "react";
import { MainContext } from "../context";
import { Row, Col, Menu, Button, Dropdown } from "antd";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { useHistory, useLocation } from "react-router-dom";

import {
  SearchOutlined,
  AppstoreOutlined,
  HomeOutlined,
} from "@ant-design/icons";
import SearchForm from "./SearchForm";
import API from "../callAPI";

const MyHeader = styled.div``;

export default function Header() {
  const [user, setUser] = useState({});

  const history = useHistory();
  // const [form] = Form.useForm();
  const location = useLocation();
  const { isLogin, setIsLogin, getCurrentUser } = useContext(MainContext);
  useEffect(async () => {}, [isLogin]);
  return (
    <div>
      <MyHeader className="w-full">
              <Menu
                // onClick={this.handleClick} selectedKeys={[current]}
                className="w-full"
                mode="horizontal"
              >
                <Menu.Item key="1" >
                  <Link to="/login">login</Link>
                </Menu.Item>
                <Menu.Item key="2">
                  <Link to="/doctor">doctor</Link>
                </Menu.Item>
                <Menu.Item key="3">
                  <Link to="/nurse">nurse</Link>
                </Menu.Item>
                <Menu.Item key="4">
                  <Link to="/patient">patient</Link>
                </Menu.Item>
                <Menu.Item key="5">
                  <Link to="/diseases">diseases</Link>
                </Menu.Item>
                <Menu.Item key="6">
                  <Link to="/medicine">medicine</Link>
                </Menu.Item>
                <Menu.Item key="7">
                  <Link to="/examination">examination</Link>
                </Menu.Item>
                <Menu.Item key="8">
                  <Link to="/diseasesmonth">diseasesmonth</Link>
                </Menu.Item>
              </Menu>
      </MyHeader>
    </div>
  );
}
