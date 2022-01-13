package io.agora.board.fast.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.herewhite.sdk.domain.ShapeType;

import io.agora.board.fast.FastRoom;
import io.agora.board.fast.R;
import io.agora.board.fast.extension.OverlayManager;
import io.agora.board.fast.model.ApplianceItem;
import io.agora.board.fast.model.FastStyle;

class ToolboxPhone implements Toolbox {
    private ToolButton toolButton;
    private ToolLayout toolsLayout;
    private ExtensionButton subToolButton;
    private ExtensionLayout extensionLayout;

    private FastRoom fastRoom;
    private OverlayManager overlayManager;

    @Override
    public void setupView(ToolboxLayout toolboxLayout) {
        Context context = toolboxLayout.getContext();

        View root = LayoutInflater.from(context).inflate(R.layout.layout_toolbox, toolboxLayout, true);
        toolButton = root.findViewById(R.id.tool_button);
        subToolButton = root.findViewById(R.id.toolbox_sub_button);
        toolsLayout = root.findViewById(R.id.tools_layout);
        toolsLayout.setOnApplianceClickListener(item -> {
            if (item == ApplianceItem.OTHER_CLEAR) {
                fastRoom.cleanScene();
            } else {
                fastRoom.setAppliance(item);
                toolButton.updateAppliance(item);
                subToolButton.setApplianceItem(item);
            }

            overlayManager.hideAll();
        });

        extensionLayout = root.findViewById(R.id.sub_tools_layout);
        extensionLayout.setType(ExtensionLayout.TYPE_PHONE);
        extensionLayout.setOnColorClickListener(color -> {
            fastRoom.setColor(color);

            subToolButton.setColor(color);
            extensionLayout.setColor(color);

            overlayManager.hideAll();
        });
        extensionLayout.setOnStrokeChangedListener(width -> {
            fastRoom.setStokeWidth(width);
        });

        toolButton.setOnClickListener(v -> {
            boolean target = !overlayManager.isShowing(OverlayManager.KEY_TOOL_LAYOUT);
            if (target) {
                overlayManager.show(OverlayManager.KEY_TOOL_LAYOUT);
            } else {
                overlayManager.hide(OverlayManager.KEY_TOOL_LAYOUT);
            }

            toolButton.setSelected(target);
            subToolButton.setSelected(false);
        });

        subToolButton.setOnSubToolClickListener(new ExtensionButton.OnSubToolClickListener() {
            @Override
            public void onDeleteClick() {
                fastRoom.getRoom().deleteOperation();
            }

            @Override
            public void onColorClick() {
                boolean target = !overlayManager.isShowing(OverlayManager.KEY_TOOL_EXTENSION);

                if (target) {
                    overlayManager.show(OverlayManager.KEY_TOOL_EXTENSION);
                } else {
                    overlayManager.hide(OverlayManager.KEY_TOOL_EXTENSION);
                }

                subToolButton.setSelected(target);
                toolButton.setSelected(false);
            }
        });
    }

    @Override
    public void setFastRoom(FastRoom fastRoom) {
        this.fastRoom = fastRoom;
        this.overlayManager = fastRoom.getOverlayManager();
        this.overlayManager.addOverlay(OverlayManager.KEY_TOOL_EXTENSION, extensionLayout);
        this.overlayManager.addOverlay(OverlayManager.KEY_TOOL_LAYOUT, toolsLayout);
    }

    @Override
    public void updateFastStyle(FastStyle fastStyle) {
        subToolButton.updateFastStyle(fastStyle);
        extensionLayout.updateFastStyle(fastStyle);
        toolButton.updateFastStyle(fastStyle);
        toolsLayout.updateFastStyle(fastStyle);
    }

    @Override
    public void updateAppliance(String appliance, ShapeType shapeType) {
        ApplianceItem item = ApplianceItem.of(appliance, shapeType);
        toolButton.updateAppliance(item);
        toolsLayout.setApplianceItem(item);
        subToolButton.setApplianceItem(item);
    }

    @Override
    public void updateStroke(int[] strokeColor, double strokeWidth) {
        int color = Color.rgb(strokeColor[0], strokeColor[1], strokeColor[2]);
        subToolButton.setColor(color);
        extensionLayout.setColor(color);
        extensionLayout.setStrokeWidth((int) strokeWidth);
    }

    @Override
    public void updateOverlayChanged(int key) {
        toolButton.setSelected(key == OverlayManager.KEY_TOOL_LAYOUT);
        subToolButton.setSelected(key == OverlayManager.KEY_TOOL_EXTENSION);
    }

    @Override
    public void setLayoutGravity(int gravity) {
        boolean isLeft = (gravity & Gravity.LEFT) == Gravity.LEFT;
        boolean isRight = (gravity & Gravity.RIGHT) == Gravity.RIGHT;

        if (isLeft) {
            RelativeLayout.LayoutParams toolBtnLp = (RelativeLayout.LayoutParams) toolButton.getLayoutParams();
            toolBtnLp.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            toolBtnLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            RelativeLayout.LayoutParams toolsLp = (RelativeLayout.LayoutParams) toolsLayout.getLayoutParams();
            toolsLp.removeRule(RelativeLayout.LEFT_OF);
            toolsLp.addRule(RelativeLayout.RIGHT_OF, toolButton.getId());

            RelativeLayout.LayoutParams subToolsLp = (RelativeLayout.LayoutParams) extensionLayout.getLayoutParams();
            subToolsLp.removeRule(RelativeLayout.LEFT_OF);
            subToolsLp.addRule(RelativeLayout.RIGHT_OF, subToolButton.getId());
        }

        if (isRight) {
            RelativeLayout.LayoutParams toolBtnLp = (RelativeLayout.LayoutParams) toolButton.getLayoutParams();
            toolBtnLp.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            toolBtnLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            RelativeLayout.LayoutParams toolsLp = (RelativeLayout.LayoutParams) toolsLayout.getLayoutParams();
            toolsLp.removeRule(RelativeLayout.RIGHT_OF);
            toolsLp.addRule(RelativeLayout.LEFT_OF, toolButton.getId());

            RelativeLayout.LayoutParams subToolsLp = (RelativeLayout.LayoutParams) extensionLayout.getLayoutParams();
            subToolsLp.removeRule(RelativeLayout.RIGHT_OF);
            subToolsLp.addRule(RelativeLayout.LEFT_OF, subToolButton.getId());
        }
    }
}
