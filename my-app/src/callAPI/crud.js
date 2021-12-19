const HOST = "http://localhost:8080/";

export const getAll = async (type) => {
    const response = await fetch(`${HOST}${type}/`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const addItem = async (obj,type) => {
    const response = await fetch(`${HOST}${type}/`, {
        method: 'POST',
        body: JSON.stringify(obj),
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const updateItem = async (obj,type,id) => {
    const response = await fetch(`${HOST}${type}/?id=${id}`, {
        method: 'PUT',
        body: JSON.stringify(obj),
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const updateDoctordiseases = async (id,type) => {
    const response = await fetch(`${HOST}doctordiseases/type/?id=${id}&&type=${type}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const deleteItem = async (type,id) => {
    const response = await fetch(`${HOST}${type}/?id=${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const findItem = async (type,searchField,value) => {
    const response = await fetch(`${HOST}${type}/find/?type=${searchField}&&value=${value}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const findlikeItem = async (type,searchField,value) => {
    const response = await fetch(`${HOST}${type}/findlike/?type=${searchField}&&value=${value}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const getDoctorDiseases = async (id) => {
    const response = await fetch(`${HOST}doctorheal/?id=${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const findDoctorDiseases = async (iddoctor,iddiseases) => {
    const response = await fetch(`${HOST}doctordiseases/findboth/?iddoctor=${iddoctor}&&iddiseases=${iddiseases}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}

export const getExamOfDoctor = async (iddoctor,dayin) => {
    const response = await fetch(`${HOST}examinationofdoctor/?id=${iddoctor}&dayin=${dayin}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },

    });
    const myJson = await response.json();

    // console.log(myJson);
    return myJson;
}