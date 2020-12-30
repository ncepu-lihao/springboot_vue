package com.li.entity.vo;

import com.li.entity.Emp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Result  {
    private Integer code;
    private String message;
    private Object data;

    public static Result success(Object data){
        Result resultEmpVo = new Result();
        resultEmpVo.setCode(200);
        resultEmpVo.setData(data);
        resultEmpVo.setMessage("success!");
        return resultEmpVo;
    }
    public static Result fail(){
        Result resultEmpVo = new Result();
        resultEmpVo.setCode(-101);
        resultEmpVo.setMessage("fail!");
        resultEmpVo.setData(null);
        return  resultEmpVo;
    }
}
