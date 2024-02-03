package com.pingpong.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "FakerReqest")
@AllArgsConstructor
public class FakerReq {

    @Schema(description = "seed", example = "1")
    private Integer seed;

    @Schema(description = "quantity", example = "1")
    private Integer quantity;

}
