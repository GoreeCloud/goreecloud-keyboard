# Spacebar cursor control — Development

This Development slice adds bounded horizontal cursor control from the existing rendered Space key without widening GoreeCloud Keyboard editor-data authority.

## Behavior

- A normal Space tap continues through the existing KeyboardView key path and commits one space.
- A horizontal-dominant drag that begins on the rendered Space accessibility target activates cursor mode after the platform touch-slop threshold.
- Cursor movement is emitted through Android DPAD LEFT/RIGHT key events using the active InputConnection.
- The gesture emits bounded incremental cursor steps and consumes the remainder of the pointer stream after cursor mode activates so release cannot also commit Space or another key.
- A vertical-dominant or equal-axis movement past activation fails closed: the pending key press is cancelled and no cursor movement or Space commit is produced.
- Cursor control is disabled while the keyboard is in its local Emoji layer so it cannot interfere with keyboard-owned emoji search/editing behavior.

## Privacy and editor-state boundary

The gesture implementation does not call surrounding-text APIs, inspect editor content, read clipboard data, persist gesture history, use a network service, emit telemetry, or add permissions. After any cursor move, locally observed Quill suggestion context is invalidated rather than reconstructed from host text. Suggestions remain fail-closed until a clean word/editor boundary starts a new local observation window.

This applies equally to password/private editors: cursor movement may still use ordinary Android navigation key events, while the existing private-input restrictions on surrounding-text inspection and suggestions remain unchanged.

## Validation boundary

JVM policy tests cover activation, directional cumulative steps, vertical/equal-axis fail-closed behavior, and invalid geometry. Android runtime tests exercise the actual rendered Space accessibility target and require ordinary tap preservation, horizontal-drag cursor routing without Space commit, and vertical-dominant cancellation.

This is Development work only. Representative physical-device ergonomics, long-text/editor compatibility, RTL/BiDi behavior, accessibility service behavior, touch-assistance/reduced-motion expectations, OEM/editor variation, performance, release, and Stable acceptance remain separate gates.
