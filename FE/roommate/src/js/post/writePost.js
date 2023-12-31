import axios from "axios";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "../../css/writePost.css";

function WritePost(){

    const formData = new FormData();
    const [postId, setPostId] = useState("");
    const [category, setCategory] = useState("");
    const [title, setTitle] = useState("");
    const [area, setArea] = useState("");
    const [fee, setFee] = useState("");
    const [body, setBody] = useState("");
    const [files, setFiles] = useState(null);
    const location = useLocation();
    const userContent = location.state;
    useEffect(() => {
        const updateBtn = document.querySelector("#updatePost_button");
        const wrtieBtn = document.querySelector("#writePost_button");
        if (userContent) {
            setPostId(userContent.postId);
            setCategory(userContent.category);
            setTitle(userContent.title);
            setBody(userContent.body);
            setArea(userContent.area);
            setFee(userContent.fee);
            setFiles(userContent.files);
            wrtieBtn.style.display = "none";
            updateBtn.style.display = "block";
        } else {
            wrtieBtn.style.display = "block";
            updateBtn.style.display = "none";
        }
    }, []);

    const handleFileChange = (e) => {
        const selectedFiles = e.target.files;
        setFiles(selectedFiles);
      };

    const handleNewPost = async(e) =>{
        e.preventDefault();
        try{
            formData.append("category", category);
            formData.append("title", title);
            formData.append("area", area);
            formData.append("fee", fee);
            formData.append("body", body);
            if(files!=null){
                for (let i = 0; i < files.length; i++) {
                    formData.append("files", files[i]);
                }
            }
            axios({
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'JWT': localStorage.getItem('JWT'),
                  },
                method : "POST",
                url : "/post",
                data : formData
            }).then((response) =>{
                console.log(response.data.data);
                window.location.href = `/posts/${category}/${response.data.data.postId}`;
            })
        }catch(error){
            console.log("글 등록 중 에러 발생  : ", error);
        }
    }

    const handleUpdatePost = async() => {
        try{
            formData.append("category", category);
            formData.append("title", title);
            formData.append("area", area);
            formData.append("fee", fee);
            formData.append("body", body);
            if(files){
                formData.append("files", files);
            }
            axios({
                method : "PATCH",
                url : `/posts/${category}/${postId}`,
                data : formData,

            }).then((response) => {
                window.location.href = `/posts/${category}/${postId}`;
            })
        }catch(error){
            console.log("게시글 수정 중에 에러 발생 : ", error);
        }
            
    }
    
    return(
        <div className="writePost_container">
            <div className="writePost_body">
                <h1>글쓰기</h1>
                <form>
                <div>
            <input type="text"
                className="writePost_input"
                placeholder="제목을 입력하세요."
                onChange={(e) => setTitle(e.target.value)}
                value={title||''}/>
                </div>
                <div className="writePost_input_middle1">
                <select
                id="writePost_input_category"
                name="writePost_category"
                className="writePost_input"
                value={category}
                onChange={(e) => setCategory(e.target.value)}
            >
              <option value="">카테고리를 선택하세요</option>
              <option value="monthly" >월세</option>
              <option value="charter" >전세</option>
            </select>
            <select
                id="writePost_input_category"
                name="writePost_category"
                className="writePost_input"
                value={area}
                onChange={(e) => setArea(e.target.value)}
            >
              <option value="">지역을 선택하세요</option>
              <option value="강남구" >강남구</option>
              <option value="강북구" >강북구</option>
              <option value="강서구" >강서구</option>
              <option value="관악구" >관악구</option>
              <option value="구로구" >구로구</option>
              <option value="금천구" >금천구</option>
              <option value="노원구" >노원구</option>
              <option value="도봉구" >도봉구</option>
              <option value="동대문구" >동대문구</option>
              <option value="동작구" >동작구</option>
              <option value="마포구" >마포구</option>
              <option value="서대문구" >서대문구</option>
              <option value="서초구" >서초구</option>
              <option value="성동구" >성동구</option>
              <option value="송파구" >송파구</option>
              <option value="양천구" >양천구</option>
              <option value="영등포구" >영등포구</option>
              <option value="용산구" >용산구</option>
              <option value="은평구" >은평구</option>
              <option value="종로구" >중구</option>
              <option value="중랑구" >중랑구</option>
            </select>
                </div>
                <div className="writePost_input_middle2">
                <input
                    type="file"
                    id="writePost_input_attachments"
                    name="attachments"
                    className="writePost_input"
                    accept="image/*,video/*"
                    onChange={handleFileChange}
/>
            <input
                type="text"
                id="writePost_input_fee"
                placeholder="fee"
                className="writePost_input"
                onChange={(e) => setFee(e.target.value)}
                value={fee||''}
            />
</div>
            <div>
            <input type="text"
            id="writePost_input_text"
                className="writePost_input"
                onChange={(e) => setBody(e.target.value)}
                value={body||''}
                placeholder="내용을 입력하세요."/>
            </div>
            <button type="button"
             id="writePost_button"
            className="writePost_button"
            onClick={handleNewPost}>
            출간하기
          </button>
          <button
          type="button"
          id="updatePost_button"
          className="writePost_button"
          style={{display:"none"}}
          onClick={handleUpdatePost}>
            수정하기
          </button>
          </form>
            </div>
        </div>
    );
};

export default WritePost;