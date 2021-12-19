import { Descriptions } from "antd";
import React from "react";

export default function Detail({ data, title }) {
  const [label, setLable] = React.useState([]);
  const [value, setValue] = React.useState([]);

  React.useEffect(() => {
    let myLabel = [],
      myValue = [];
    for (const key in data) {
      if (key != "key") {
        myLabel.push(key);
        myValue.push(data[key]);
      }
    }
    setLable(myLabel);
    setValue(myValue);
  }, [data]);
  return (
    <div>
      <Descriptions title={title}>
        {label.map((val, index) => {
          return (
            <Descriptions.Item label={val}>{value[index]}</Descriptions.Item>
          );
        })}
      </Descriptions>
      <hr></hr>
    </div>
  );
}
