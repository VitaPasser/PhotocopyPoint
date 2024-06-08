package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Controller.DTO.OrderView;
import org.vitapasser.photocopypoint.Controller.DTO.TypeView;

import java.util.List;

public record OrderViewAndTypesViews(OrderView orderView, List<TypeView> typeViews) {
}
