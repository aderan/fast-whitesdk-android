package io.agora.board.fast.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.agora.board.fast.R;
import io.agora.board.fast.model.FastWindowBoxState;

/**
 * @author fenglibin
 */
public class FastRoomController extends RoomControllerGroup {
    private RedoUndoLayout redoUndoLayout;
    private ScenesLayout scenesLayout;
    private ToolboxLayout toolboxLayout;

    public FastRoomController(ViewGroup root) {
        super(root);
        setupView();
    }

    private void setupView() {
        LayoutInflater.from(context).inflate(R.layout.layout_fast_room_controller, root, true);

        redoUndoLayout = root.findViewById(R.id.fast_redo_undo_layout);
        scenesLayout = root.findViewById(R.id.fast_scenes_layout);
        toolboxLayout = root.findViewById(R.id.fast_toolbox_layout);

        addController(redoUndoLayout);
        addController(scenesLayout);
        addController(toolboxLayout);
    }

    @Override
    public void updateWindowBoxState(String windowBoxState) {
        super.updateWindowBoxState(windowBoxState);
        FastWindowBoxState boxState = FastWindowBoxState.of(windowBoxState);
        switch (boxState) {
            case Maximized:
                redoUndoLayout.hide();
                scenesLayout.hide();
                break;
            case Minimized:
            case Normal:
                redoUndoLayout.show();
                scenesLayout.show();
                break;
        }
    }
}
