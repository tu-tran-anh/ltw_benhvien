import React, { useState, useEffect } from "react";
import { Modal, Checkbox, Divider, Button, Row ,Col, message} from "antd";
import API from "../../callAPI";

const CheckboxGroup = Checkbox.Group;
export default function DoctorAndDiseases({
  isModalVisible,
  setIsModalVisible,
  data,
}) {
  const [diseases, setDiseases] = useState([]);

  useEffect(async () => {
    await callData();
  }, [isModalVisible]);

  const submit = async ()=>{
    let allDoctorDiseases = await API.CRUD.findItem("doctordiseases","iddoctor",data.iddoctor);
    allDoctorDiseases = JSON.parse(allDoctorDiseases.data);

    let allIddiseases = checkedList.filter((value)=> value).map((value)=>{
        return Number(value.split(":")[0]);
    });

    let a = await API.CRUD.updateDoctordiseases(data.iddoctor,"not");
    console.log("a:",a);

    for(let i = 0; i<allIddiseases.length;i++){
        for(let j = 0; j<allDoctorDiseases.length;j++){
            if(allIddiseases[i] == allDoctorDiseases[j].iddiseases){
                allDoctorDiseases[j].type = "active";
                await API.CRUD.updateItem(allDoctorDiseases[j],"doctordiseases",allDoctorDiseases[j].iddoctordiseases);
                break;
            }
            if(j == allDoctorDiseases.length-1){
                await API.CRUD.addItem({
                    "iddoctor":data.iddoctor,
                    "iddiseases":allIddiseases[i],
                    "type":"active"
                },"doctordiseases");
            }
        }
    }
  }

  const callData = async ()=>{
    if(!data){
        return 0;
    }else{
        console.log("data vao: ",data);
    }
    let allDiseases = await API.CRUD.getAll("diseases");
    if (allDiseases.status == 200) {
      allDiseases = JSON.parse(allDiseases.data);
      let mydata = allDiseases.map((value)=>{
        if(value.type == "active"){
            return value.iddiseases+":"+value.name;
        }else{
            return null;
        }
      })
      mydata = mydata.filter((value)=> value!=null);
      setDiseases(mydata);
    }
    
    let allDoctorDiseases = await API.CRUD.findItem("doctordiseases","iddoctor",data.iddoctor);
    allDoctorDiseases = JSON.parse(allDoctorDiseases.data);
    setCheckedList(allDoctorDiseases.map((value)=>{
        if(value.type == 'active'){
            for(let i = 0; i<allDiseases.length;i++){
                if(value.iddiseases == allDiseases[i].iddiseases){
                    console.log("default: ",allDiseases[i].iddiseases+":"+allDiseases[i].name);
                    return allDiseases[i].iddiseases+":"+allDiseases[i].name;
                }
            }return "not found";
        }
    }))
  }

  const [checkedList, setCheckedList] = React.useState([]);
  const [indeterminate, setIndeterminate] = React.useState(true);
  const [checkAll, setCheckAll] = React.useState(false);

  const onChange = (list) => {
    setCheckedList(list);
    setIndeterminate(!!list.length && list.length < diseases.length);
    setCheckAll(list.length === diseases.length);
  };

  const onCheckAllChange = (e) => {
    setCheckedList(e.target.checked ? diseases : []);
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
        <Checkbox
          indeterminate={indeterminate}
          onChange={onCheckAllChange}
          checked={checkAll}
        >
          Check all
        </Checkbox>
        <Divider />
        <CheckboxGroup
          options={diseases}
          value={checkedList}
          onChange={onChange}
        />
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
                await callData();
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
